-- Inserindo perfis
insert into perfis(nome) values ('ROLE_ADMIN');
insert into perfis(nome) values ('ROLE_USER');

-- Inserindo usuários
insert into usuarios(dtype, nome, sobrenome, email, senha, is_confirmado)
values ('Usuario', 'Admin', 'do Sistema', 'admin@email.com', '$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe', true);

insert into usuarios(dtype, nome, sobrenome, email, senha, is_confirmado)
values ('Usuario', 'Usuario', 'do Sistema', 'user@email.com', '$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe', true);

-- Associando usuários aos perfis
insert into usuario_perfil(usuario_id, perfil_id) values(1, 1);
insert into usuario_perfil(usuario_id, perfil_id) values(2, 2);
