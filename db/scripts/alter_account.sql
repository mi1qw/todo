alter table item add column if not exists account_id bigint;
alter table item add constraint FK_ITEM_ON_ACCOUNT foreign key (account_id) references account (id);