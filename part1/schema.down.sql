
    set client_min_messages = WARNING;

    alter table if exists CreditCard 
       drop constraint if exists FKe2wqhnxpkjp87b3eyfq6gl39a;

    alter table if exists CreditCard 
       drop constraint if exists FKfky0y471d7n8cebc1oe28idci;

    alter table if exists customer_address 
       drop constraint if exists FK4n1t9cnxhkev5vdbedu0ao068;

    alter table if exists customer_address 
       drop constraint if exists FK7dy564lg4tac3m50d3ws4mhc4;

    alter table if exists customer_creditcard 
       drop constraint if exists FKt3ayxa3h262vhdbal2bldrhgt;

    alter table if exists customer_creditcard 
       drop constraint if exists FKfjq2xyx1jh83r4h1t7f18kxj;

    drop table if exists Address cascade;

    drop table if exists Bank cascade;

    drop table if exists CreditCard cascade;

    drop table if exists Customer cascade;

    drop table if exists customer_address cascade;

    drop table if exists customer_creditcard cascade;

    drop table if exists Pincode cascade;
