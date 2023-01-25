/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     1/23/2023 10:40:33 PM                        */
/*==============================================================*/


drop index R_TAREA_CATEGORIA_FK;

drop index CATEGORIA_PK;

drop table CATEGORIA;

drop index PROYECTO_PK;

drop table PROYECTO;

drop index R_TAREA_RECORD_FK;

drop index RECORDATORIO_PK;

drop table RECORDATORIO;

drop index R_TAREA_USR_FK;

drop index R_TAREA_PRY_FK;

drop index TAREA_PK;

drop table TAREA;

drop index USUARIO_PK;

drop table USUARIO;

/*==============================================================*/
/* Table: CATEGORIA                                             */
/*==============================================================*/
create table CATEGORIA (
   IDCATEGORIA          SERIAL               not null,
   IDTAREA              INT4                 null,
   NOMBRECATEGORIA      TEXT                 not null,
   constraint PK_CATEGORIA primary key (IDCATEGORIA)
);

/*==============================================================*/
/* Index: CATEGORIA_PK                                          */
/*==============================================================*/
create unique index CATEGORIA_PK on CATEGORIA (
IDCATEGORIA
);

/*==============================================================*/
/* Index: R_TAREA_CATEGORIA_FK                                  */
/*==============================================================*/
create  index R_TAREA_CATEGORIA_FK on CATEGORIA (
IDTAREA
);

/*==============================================================*/
/* Table: PROYECTO                                              */
/*==============================================================*/
create table PROYECTO (
   IDPRY                SERIAL               not null,
   NOMBREPRY            TEXT                 not null,
   constraint PK_PROYECTO primary key (IDPRY)
);

/*==============================================================*/
/* Index: PROYECTO_PK                                           */
/*==============================================================*/
create unique index PROYECTO_PK on PROYECTO (
IDPRY
);

/*==============================================================*/
/* Table: RECORDATORIO                                          */
/*==============================================================*/
create table RECORDATORIO (
   IDRECORD             SERIAL               not null,
   IDTAREA              INT4                 not null,
   FECHARECORD          DATE                 not null,
   HORARECOR            TIME                 not null,
   constraint PK_RECORDATORIO primary key (IDRECORD)
);

/*==============================================================*/
/* Index: RECORDATORIO_PK                                       */
/*==============================================================*/
create unique index RECORDATORIO_PK on RECORDATORIO (
IDRECORD
);

/*==============================================================*/
/* Index: R_TAREA_RECORD_FK                                     */
/*==============================================================*/
create  index R_TAREA_RECORD_FK on RECORDATORIO (
IDTAREA
);

/*==============================================================*/
/* Table: TAREA                                                 */
/*==============================================================*/
create table TAREA (
   IDTAREA              SERIAL               not null,
   IDUSR                INT4                 null,
   IDPRY                INT4                 not null,
   TITULOTAREA          TEXT                 not null,
   DESCTAREA            TEXT                 null,
   FECHALIMITE          DATE                 not null,
   PRIORIDAD            CHAR(1)              not null,
   ESTACOMPLETADA       BOOL                 not null,
   constraint PK_TAREA primary key (IDTAREA)
);

/*==============================================================*/
/* Index: TAREA_PK                                              */
/*==============================================================*/
create unique index TAREA_PK on TAREA (
IDTAREA
);

/*==============================================================*/
/* Index: R_TAREA_PRY_FK                                        */
/*==============================================================*/
create  index R_TAREA_PRY_FK on TAREA (
IDPRY
);

/*==============================================================*/
/* Index: R_TAREA_USR_FK                                        */
/*==============================================================*/
create  index R_TAREA_USR_FK on TAREA (
IDUSR
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   IDUSR                SERIAL               not null,
   NOMBREUSR            TEXT                 not null,
   CORREOUSR            TEXT                 not null,
   PSWUSR               TEXT                 not null,
   constraint PK_USUARIO primary key (IDUSR)
);

/*==============================================================*/
/* Index: USUARIO_PK                                            */
/*==============================================================*/
create unique index USUARIO_PK on USUARIO (
IDUSR
);

alter table CATEGORIA
   add constraint FK_CATEGORI_R_TAREA_C_TAREA foreign key (IDTAREA)
      references TAREA (IDTAREA)
      on delete restrict on update restrict;

alter table RECORDATORIO
   add constraint FK_RECORDAT_R_TAREA_R_TAREA foreign key (IDTAREA)
      references TAREA (IDTAREA)
      on delete restrict on update restrict;

alter table TAREA
   add constraint FK_TAREA_R_TAREA_P_PROYECTO foreign key (IDPRY)
      references PROYECTO (IDPRY)
      on delete restrict on update restrict;

alter table TAREA
   add constraint FK_TAREA_R_TAREA_U_USUARIO foreign key (IDUSR)
      references USUARIO (IDUSR)
      on delete restrict on update restrict;

