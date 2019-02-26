begin;

delete from cities sc where sc.id not in (
select min(ds.id) from cities ds group by ds.name );
/*
  Что делать с null в name?
  В колонке null, т.е. имени нет. Логично удалить, добавив во вложеный запрос  "where ds.name is not null"
*/

select * from cities;


/*
  rollback or commit
*/