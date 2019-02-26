/*
  Замечание:
  В запросе на создание таблицы population в строке
    insert into population(country, city, citizen) values ('usa', orlando, '150');
  ошибка.
*/

with pop_contry as (select conp.country, sum(conp.citizen) as all_citizens from population conp group by conp.country)
select csp.country, csp.city, round(csp.citizen / pc.all_citizens * 100, 2) as citizenship
from population csp inner join pop_contry pc on csp.country = pc.country
where csp.city in (select cc.city from population cc where cc.country = csp.country order by cc.citizen desc limit 3)
order by csp.country, citizenship desc;