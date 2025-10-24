-- ==========================================
-- 🔄 RECRIAÇÃO DO BANCO DE DADOS
-- ==========================================
DROP DATABASE IF EXISTS vcore;
CREATE DATABASE vcore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vcore;

-- ==========================================
-- 🧱 TABELA: perfis
-- ==========================================
CREATE TABLE perfis (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL UNIQUE
);

-- ==========================================
-- 🧱 TABELA: usuarios
-- ==========================================
CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          dtype VARCHAR(50),
                          nome VARCHAR(100) NOT NULL,
                          sobrenome VARCHAR(100),
                          email VARCHAR(150) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          is_confirmado BOOLEAN DEFAULT FALSE
);

-- ==========================================
-- 🧱 TABELA: vtuber
-- (caso você já tenha um modelo Vtuber com UID)
-- ==========================================
CREATE TABLE vtuber (
                        uid BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(150) NOT NULL,
                        descricao TEXT,
                        canal VARCHAR(255),
                        media_geral DECIMAL(3,2) DEFAULT 0
);

-- ==========================================
-- 🔗 TABELA: usuario_perfil (N:N)
-- ==========================================
CREATE TABLE usuario_perfil (
                                usuario_id BIGINT NOT NULL,
                                perfil_id BIGINT NOT NULL,
                                PRIMARY KEY (usuario_id, perfil_id),
                                CONSTRAINT fk_usuario_perfil_usuario FOREIGN KEY (usuario_id)
                                    REFERENCES usuarios(id) ON DELETE CASCADE,
                                CONSTRAINT fk_usuario_perfil_perfil FOREIGN KEY (perfil_id)
                                    REFERENCES perfis(id) ON DELETE CASCADE
);

-- ==========================================
-- 🔗 TABELA: usuario_vtuber_favoritos (N:N)
-- ==========================================
CREATE TABLE usuario_vtuber_favoritos (
                                          usuario_id BIGINT NOT NULL,
                                          vtuber_uid BIGINT NOT NULL,
                                          PRIMARY KEY (usuario_id, vtuber_uid),
                                          CONSTRAINT fk_favoritos_usuario FOREIGN KEY (usuario_id)
                                              REFERENCES usuarios(id) ON DELETE CASCADE,
                                          CONSTRAINT fk_favoritos_vtuber FOREIGN KEY (vtuber_uid)
                                              REFERENCES vtuber(uid) ON DELETE CASCADE
);

-- ==========================================
-- 👥 INSERINDO PERFIS PADRÃO
-- ==========================================
INSERT INTO perfis (nome) VALUES ('ROLE_ADMIN');
INSERT INTO perfis (nome) VALUES ('ROLE_USER');

-- ==========================================
-- 👤 INSERINDO USUÁRIOS PADRÃO
-- (senha: "123456" codificada em BCrypt)
-- ==========================================
INSERT INTO usuarios (dtype, nome, sobrenome, email, senha, is_confirmado)
VALUES ('Usuario', 'Admin', 'do Sistema', 'admin@email.com',
        '$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe', TRUE);

INSERT INTO usuarios (dtype, nome, sobrenome, email, senha, is_confirmado)
VALUES ('Usuario', 'Usuario', 'do Sistema', 'user@email.com',
        '$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe', TRUE);

-- ==========================================
-- 🔗 ASSOCIANDO PERFIS AOS USUÁRIOS
-- ==========================================
INSERT INTO usuario_perfil (usuario_id, perfil_id)
VALUES (
           (SELECT id FROM usuarios WHERE email = 'admin@email.com'),
           (SELECT id FROM perfis WHERE nome = 'ROLE_ADMIN')
       );

INSERT INTO usuario_perfil (usuario_id, perfil_id)
VALUES (
           (SELECT id FROM usuarios WHERE email = 'user@email.com'),
           (SELECT id FROM perfis WHERE nome = 'ROLE_USER')
       );
-- ==========================================
-- 🏢 TABELA: empresas
-- ==========================================
CREATE TABLE empresas (
                          uid VARCHAR(255) PRIMARY KEY,
                          nome VARCHAR(150) NOT NULL,
                          url_foto VARCHAR(500),
                          info TEXT
);

-- ==========================================
-- 🔗 RELACIONAMENTO: Empresa 1:N Vtuber
-- ==========================================
ALTER TABLE vtuber
    ADD COLUMN empresa_uid VARCHAR(255),
    ADD CONSTRAINT fk_vtuber_empresa FOREIGN KEY (empresa_uid)
        REFERENCES empresas(uid) ON DELETE SET NULL;

-- ==========================================
-- 🧾 TABELA: notas
-- ==========================================
CREATE TABLE notas (
                       uid VARCHAR(255) PRIMARY KEY,
                       vtuberId BIGINT NOT NULL,
                       valor INT CHECK (valor BETWEEN 0 AND 5),
                       comentario TEXT,
                       data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                       data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT fk_nota_vtuber FOREIGN KEY (vtuberId)
                           REFERENCES vtuber(uid) ON DELETE CASCADE
);

-- ==========================================
-- 🔗 TABELA: nota_usuarios (N:N)
-- ==========================================
CREATE TABLE nota_usuarios (
                               nota_uid VARCHAR(255) NOT NULL,
                               usuario_id BIGINT NOT NULL,
                               PRIMARY KEY (nota_uid, usuario_id),
                               CONSTRAINT fk_nota_usuario_nota FOREIGN KEY (nota_uid)
                                   REFERENCES notas(uid) ON DELETE CASCADE,
                               CONSTRAINT fk_nota_usuario_usuario FOREIGN KEY (usuario_id)
                                   REFERENCES usuarios(id) ON DELETE CASCADE
);
