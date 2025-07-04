create table pacientes (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo varchar(100) not null unique,
    telefono varchar(100) not null unique,
    documento varchar(10) not null unique,
    calle varchar(100) not null,
    barrio varchar(100) not null,
    cod_postal varchar(100) not null,
    complemento varchar(100),
    numero int,
    estado varchar(100) not null,
    ciudad varchar(100) not null,

    primary key(id)
);