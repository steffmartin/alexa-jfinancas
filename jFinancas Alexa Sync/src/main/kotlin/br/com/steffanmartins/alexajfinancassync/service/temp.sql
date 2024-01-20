Hibernate
:
select jce1_0.id,
       jce1_0.id_cliente,
       jce1_0.id_conta,
       jce1_0.fimfrequencia,
       jce1_0.historico,
       jce1_0.id_planconta,
       jce1_0.status,
       jce1_0.id_tipopgto,
       jce1_0.valor,
       jce1_0.vencimento
from cpagar jce1_0
         join conta c1_0 on c1_0.id = jce1_0.id_conta
         join tipoconta tc1_0 on tc1_0.id = c1_0.id_tipoconta
where jce1_0.status = ?
  and jce1_0.fimfrequencia = ?
  and tc1_0.descricao = ?
