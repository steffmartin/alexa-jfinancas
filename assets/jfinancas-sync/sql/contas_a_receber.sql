SELECT PCPAI.NOME,
       '|',
       PC.NOME,
       '|',
       CR.HISTORICO,
       '|',
       CR.VENCIMENTO,
       '|',
       C.NOME,
       '|',
       CLI.NOMEFANTASIA,
       '|',
       CAST(CR.VALOR AS NUMERIC(18, 2))
FROM CRECEBER cr
         JOIN CLIENTE cli ON cr.ID_CLIENTE = cli.ID
         JOIN conta c ON cr.ID_CONTA = c.ID
         JOIN PLANODECONTAS pc ON cr.ID_PLANCONTA = pc.ID
         JOIN PLANODECONTAS pcpai ON pc.ID_OWNER = PCPAI.ID
         LEFT JOIN TIPOPAGAMENTO tp ON cr.ID_TIPOPGTO = tp.ID
WHERE status = 0
  AND FIMFREQUENCIA = 0
  AND (tp.DESCRICAO IS NULL OR tp.DESCRICAO <> 'Cartão de Crédito')
  AND c.NOME NOT LIKE '%Salário%'
ORDER BY VENCIMENTO;