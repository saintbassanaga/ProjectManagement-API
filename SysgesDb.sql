-- we don't know how to generate root <with-no-name> (class Root) :(

grant select on performance_schema.* to 'mysql.session'@localhost;

grant trigger on sys.* to 'mysql.sys'@localhost;

grant audit_abort_exempt, firewall_exempt, select, system_user on *.* to 'mysql.infoschema'@localhost;

grant audit_abort_exempt, authentication_policy_admin, backup_admin, clone_admin, connection_admin, firewall_exempt, persist_ro_variables_admin, session_variables_admin, shutdown, super, system_user, system_variables_admin on *.* to 'mysql.session'@localhost;

grant audit_abort_exempt, firewall_exempt, system_user on *.* to 'mysql.sys'@localhost;

grant alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_user_id, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, telemetry_log_admin, trigger, update, xa_recover_admin, grant option on *.* to root@localhost;

create table project
(
    uuid          binary(16)                                  not null
        primary key,
    description   varchar(255)                                null,
    end_date      datetime(6)                                 null,
    project_state enum ('RUNNING', 'SUSPENDED', 'TERMINATED') null,
    start_date    datetime(6)                                 null,
    titre         varchar(255)                                null,
    version       int                                         null,
    constraint idx_project_version_unq
        unique (version)
);

create index Idx_project_states
    on project (start_date, end_date, project_state);

create table user
(
    creation_date datetime(6)                                 null,
    username      varchar(15)                                 not null,
    uuid          binary(16)                                  not null
        primary key,
    email         varchar(25)                                 not null,
    password      varchar(100)                                not null,
    name          varchar(255)                                null,
    surname       varchar(255)                                null,
    role          enum ('ADMIN', 'MEMBER', 'PROJECT_MANAGER') null,
    constraint UKsb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

create table project_users
(
    project_uuid binary(16) not null,
    users_uuid   binary(16) not null,
    primary key (project_uuid, users_uuid),
    constraint FK8b4ut1vvb98cxxclfea8b7vwl
        foreign key (project_uuid) references project (uuid),
    constraint FKcoxja90wyerdbp8o6jrjhstvi
        foreign key (users_uuid) references user (uuid)
);

create table task
(
    uuid          binary(16)                        not null
        primary key,
    creation_date datetime(6)                       null,
    description   varchar(255)                      null,
    due_date      datetime(6)                       null,
    severity      enum ('HIGH', 'LOW', 'MEDIUM')    null,
    state         enum ('ENDED', 'RUNNING', 'TODO') null,
    title         varchar(255)                      null,
    project_uuid  binary(16)                        not null,
    user_uuid     binary(16)                        not null,
    constraint Idx_task_user_uuid
        unique (user_uuid, due_date, creation_date),
    constraint idx_task_project_uuid_unq
        unique (project_uuid),
    constraint FK71hev67nomujapsgh88ky2t32
        foreign key (project_uuid) references project (uuid),
    constraint FK7esxifaqpwlir473qeddcvm9p
        foreign key (user_uuid) references user (uuid)
);

create table comment
(
    uuid               binary(16)   not null
        primary key,
    creation_date      datetime(6)  null,
    content            varchar(255) null,
    project_uuid       binary(16)   null,
    task_uuid          binary(16)   null,
    user_uuid          binary(16)   not null,
    last_modified_date datetime(6)  null,
    constraint UKj3p4uoox2mcdi7mxump5ok5ke
        unique (task_uuid, project_uuid),
    constraint UKjs5xecdyc0mb7g6ml9nttwbq1
        unique (project_uuid),
    constraint UKk227npwk2bma118pvduw5a2ny
        unique (user_uuid),
    constraint UKsblksc65ibqwip6q9nium2rtx
        unique (task_uuid),
    constraint FKek7o50nua63esew5jfhpag0ct
        foreign key (task_uuid) references task (uuid),
    constraint FKhfs2kkpjwut2mluq5shd8m0ya
        foreign key (user_uuid) references user (uuid),
    constraint FKi4m6k21r2dimxd8aq95mupvai
        foreign key (project_uuid) references project (uuid)
);

create index idx_user_email
    on user (email);

create index idx_username_creation_date
    on user (creation_date);

