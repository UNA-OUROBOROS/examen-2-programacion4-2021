create schema tienda_mascotas;
use tienda_mascotas;

-- productos

create table productos
(
    id_producto int          not null auto_increment primary key ,
    nombre      varchar(255) not null,
    descripcion varchar(500) not null,
    url         VARCHAR(500) not null,
    precio      double       not null,
    stock       int          not null
);

-- categorias
create table categorias
(
    id_categoria int          not null auto_increment primary key,
    descripcion  varchar(500) not null
);

-- relacion muchos a muchos productos-categorias

create table productos_categorias
(
    id_producto  int not null,
    id_categoria int not null
);

-- restricciones de tablas

-- productos_categorias
alter table productos_categorias
    add constraint fk_productos_categorias_productos
        foreign key (id_producto) references productos (id_producto);
alter table productos_categorias
    add constraint fk_productos_categorias_categorias
        foreign key (id_categoria) references categorias (id_categoria);
-- debe ser unico la combinacion de producto y categoria
alter table productos_categorias
    add constraint uq_productos_categorias
        unique (id_producto, id_categoria);
