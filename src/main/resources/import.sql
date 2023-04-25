insert into papel(id,papel) values (1, 'ADMIN')
insert into papel(id,papel) values (2, 'USER')

insert into usuario(login,senha,nome, papel_id) values ('convidado', '$2y$10$VSFoT0EJwaUgA4.4UUuZB.FGyHS96BNuKN43svaTgIY0STwW95qhq', 'Usuário convidado', 2);
insert into usuario(login,senha,nome, papel_id) values ('admin', '$2y$10$m3MAMFp0fmJs/XAJaTrrdOBkhtFXBtaZ0EmSIP2ql0nhyCWcj2iMG', 'Gestor', 1);


insert into pais(nome,sigla,gentilico) values ('Brasil', 'BR', 'Brasileiro');
insert into pais(nome,sigla,gentilico) values ('Argentina', 'AR', 'Argentino');
insert into pais(nome,sigla,gentilico) values ('Alemanha', 'AL', 'Alemao');
insert into pais(nome,sigla,gentilico) values ('Venezuela', 'VE', 'Venezuelano');
insert into pais(nome,sigla,gentilico) values ('China', 'CH', 'Chines');
