-- ==========================================
-- 1. 🔄 LIMPEZA E RECRIAÇÃO DO BANCO (Obrigatório)
-- Isso resolve todos os problemas de chaves estrangeiras pendentes.
-- ==========================================
DROP DATABASE IF EXISTS vcore;
CREATE DATABASE vcore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vcore;

-- ==========================================
-- 2. 🧱 DDL: CRIAÇÃO DE TABELAS (Padronizadas com JPA: PLURAL)
-- ==========================================

-- Tabela: perfis
CREATE TABLE perfis (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela: usuarios (inclui coluna dtype para herança)
CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          dtype VARCHAR(50),
                          nome VARCHAR(100) NOT NULL,
                          sobrenome VARCHAR(100),
                          email VARCHAR(150) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          is_confirmado BOOLEAN DEFAULT FALSE
);

-- Tabela: empresas
CREATE TABLE empresas (
                          uid VARCHAR(255) PRIMARY KEY,
                          nome VARCHAR(150) NOT NULL,
                          url_foto VARCHAR(500),
                          info TEXT
);

-- Tabela: vtubers (Plural, como na Entidade Java)
CREATE TABLE vtubers (
                         uid VARCHAR(255) PRIMARY KEY,
                         nome VARCHAR(150) NOT NULL,
                         url_foto VARCHAR(500),
                         info TEXT,
                         link_canal VARCHAR(255),
                         video_youtube VARCHAR(255),
                         media_notas FLOAT(23),
                         total_avaliacoes INTEGER,
                         soma_total_das_notas FLOAT(23),
                         empresa_id VARCHAR(255) NOT NULL
);

-- Tabela: notas
CREATE TABLE notas (
                       uid VARCHAR(255) PRIMARY KEY,
                       vtuber_id VARCHAR(255) NOT NULL,
                       valor INT CHECK (valor BETWEEN 0 AND 5),
                       comentario TEXT,
                       data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                       data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela Associativa: usuario_perfil (N:N)
CREATE TABLE usuario_perfil (
                                usuario_id BIGINT NOT NULL,
                                perfil_id BIGINT NOT NULL,
                                PRIMARY KEY (usuario_id, perfil_id)
);

-- Tabela Associativa: usuario_vtuber_favoritos (ElementCollection de String na entidade Usuario)
CREATE TABLE usuario_vtuber_favoritos (
                                          usuario_id BIGINT NOT NULL,
                                          vtuber_uid VARCHAR(255)
);

-- Tabela: produtos (exemplo)
CREATE TABLE produtos (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          descricao VARCHAR(255),
                          estoque INTEGER,
                          nome VARCHAR(255),
                          situacao BIT,
                          valor_de_compra DECIMAL(38,2),
                          valor_de_venda DECIMAL(38,2),
                          PRIMARY KEY (id)
);

-- ==========================================
-- 3. 🔗 DDL: CHAVES ESTRANGEIRAS
-- ==========================================

-- Chaves Estrangeiras para Tabela notas
ALTER TABLE notas
    ADD CONSTRAINT fk_nota_vtuber FOREIGN KEY (vtuber_id)
        REFERENCES vtubers(uid) ON DELETE CASCADE;

-- Chaves Estrangeiras para Tabela vtubers
ALTER TABLE vtubers
    ADD CONSTRAINT fk_vtuber_empresa FOREIGN KEY (empresa_id)
        REFERENCES empresas(uid) ON DELETE CASCADE;

-- Chaves Estrangeiras para Tabela usuario_perfil
ALTER TABLE usuario_perfil
    ADD CONSTRAINT fk_usuario_perfil_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id) ON DELETE CASCADE;
ALTER TABLE usuario_perfil
    ADD CONSTRAINT fk_usuario_perfil_perfil FOREIGN KEY (perfil_id)
        REFERENCES perfis(id) ON DELETE CASCADE;

-- Chaves Estrangeiras para Tabela usuario_vtuber_favoritos
ALTER TABLE usuario_vtuber_favoritos
    ADD CONSTRAINT fk_favoritos_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id) ON DELETE CASCADE;
-- Nota: fk_favoritos_vtuber não é mais necessária, pois vtuber_uid é apenas uma string ID.


-- ==========================================
-- 4. 👥 DML: INSERÇÃO DE DADOS
-- ==========================================

-- Inserindo perfis
insert into perfis(nome) values ('ROLE_ADMIN');
insert into perfis(nome) values ('ROLE_USER');

-- Inserindo usuários (senha: "123456" codificada em BCrypt)
insert into usuarios(dtype, nome, sobrenome, email, senha, is_confirmado)
values ('Usuario', 'Admin', 'do Sistema', 'admin@email.com', '$2a$10$944hSl4M26XD1d8gEa/KWuhIBuN5F0Bs/LSE7./ZDUFHfdzhgzd7q', true);

insert into usuarios(dtype, nome, sobrenome, email, senha, is_confirmado)
values ('Usuario', 'Usuario', 'do Sistema', 'user@email.com', '$2a$10$944hSl4M26XD1d8gEa/KWuhIBuN5F0Bs/LSE7./ZDUFHfdzhgzd7q', true);

-- Associando usuários aos perfis
insert into usuario_perfil(usuario_id, perfil_id) values(1, 1);
insert into usuario_perfil(usuario_id, perfil_id) values(2, 2);

-- Inserindo dados na tabela 'empresas'
INSERT INTO empresas (uid, nome, info)
VALUES
    ('emp001', 'Empresa Exemplo 1', 'Informações sobre a Empresa Exemplo 1'),
    ('emp002', 'Empresa Exemplo 2', 'Informações sobre a Empresa Exemplo 2'),
    ('emp003', 'Empresa Exemplo 3', 'Informações sobre a Empresa Exemplo 3');

-- Inserindo dados na tabela 'vtubers'
INSERT INTO vtubers (uid, empresa_id, nome, info, media_notas, total_avaliacoes, soma_total_das_notas)
VALUES
    ('v001', 'emp001', 'Vtuber A', 'Info A', 4.5, 10, 45.0),
    ('v002', 'emp001', 'Vtuber B', 'Info B', 3.8, 5, 19.0),
    ('v003', 'emp002', 'Vtuber C', 'Info C', 5.0, 1, 5.0);