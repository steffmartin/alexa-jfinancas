SELECT tc.DESCRICAO,
       '|',
       c.NOME,
       '|',
       tp.VENCIMENTO,
       '|',
       tp.HISTORICO,
       '|',
       CAST(tp.VALOR AS NUMERIC(18, 2))
FROM TRANSF_PROG tp
         JOIN conta c ON tp.ID_CONTA_TO = c.ID
         JOIN TIPOCONTA tc ON c.ID_TIPOCONTA = tc.ID
WHERE status = 0
  AND FIMFREQUENCIA = 0
ORDER BY VENCIMENTO;