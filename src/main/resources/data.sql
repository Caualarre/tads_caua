-- ==========================================
-- data.sql - DML (Data Manipulation Language)
-- AJUSTADO PARA PKs DO TIPO STRING ('uid')
-- ==========================================

-- 1. INSERINDO PERFIS (Tabela: perfis)
-- PK: uid (String)
INSERT INTO perfis (uid, nome) VALUES ('ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO perfis (uid, nome) VALUES ('ROLE_USER', 'ROLE_USER');

-- 2. INSERINDO USUÁRIOS (Tabela: usuarios)
-- PK: uid (String) - usando IDs de string explícitas
-- Senha para ambos: "123456" codificada em BCrypt.
INSERT INTO usuarios (uid, dtype, nome, sobrenome, email, senha, is_confirmado)
VALUES ('admin001', 'Usuario', 'Admin', 'do Sistema', 'admin@email.com', '$2a$10$kmzjw/pyVvv8jYi0V6WMA.Edq4enmP2s/0IOfixpj2blv2GFLp0oa', true);

INSERT INTO usuarios (uid, dtype, nome, sobrenome, email, senha, is_confirmado)
VALUES ('user001', 'Usuario', 'Usuario', 'do Sistema', 'user@email.com', '$2a$10$kmzjw/pyVvv8jYi0V6WMA.Edq4enmP2s/0IOfixpj2blv2GFLp0oa', true);

-- 3. INSERINDO EMPRESAS (Tabela: empresas)
INSERT INTO empresas (uid, nome, info, url_foto)
VALUES
    ('emp001', 'Hololive Production', 'Agência japonesa líder em Vtubers.', NULL);

-- 4. INSERINDO VTUBERS (Tabela: vtubers)
INSERT INTO vtubers (uid, nome, empresa_id, info, url_foto, link_canal, video_youtube, media_notas, total_avaliacoes, soma_total_das_notas)
VALUES
    ('vt001', 'Houshou Marine', 'emp001', 'A capitã pirata mais preguiçosa.', 'url_marine', 'canal_marine', 'video_marine', 4.5, 2, 9.0);

-- 5. INSERINDO NOTAS (Tabela: notas)
INSERT INTO notas (uid, vtuber_id, valor, comentario, data_criacao, data_atualizacao)
VALUES
    ('nota001', 'vt001', 5, 'Melhor Vtuber! Senchou!', NOW(), NOW());

-- 6. INSERINDO DADOS NAS TABELAS DE JUNÇÃO

-- A. JUNÇÃO USUÁRIO X PERFIL (Tabela: usuario_perfil)
-- usa admin001 -> ROLE_ADMIN, user001 -> ROLE_USER
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES ('admin001', 'ROLE_ADMIN');
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES ('user001', 'ROLE_USER');

-- B. JUNÇÃO NOTA X USUÁRIO (Tabela: nota_usuarios)
-- nota001 feita pelo admin001
INSERT INTO nota_usuarios (nota_uid, usuario_id) VALUES ('nota001', 'admin001');

-- C. JUNÇÃO USUÁRIO X VTUBER FAVORITOS (Tabela: usuario_favoritos)
-- user001 favoritou vt001
INSERT INTO usuario_favoritos (usuario_uid, vtuber_uid) VALUES ('user001', 'vt001');

-- 7. INSERINDO PRODUTOS (Tabela: produtos)
-- Assumindo que 'id' desta tabela é uma PK numérica simples, não String.
INSERT INTO produtos (descricao, estoque, nome, situacao, valor_de_compra, valor_de_venda)
VALUES
    ('Caneca de café da Senchou', 50, 'Produto 1', 1, 15.00, 30.00);