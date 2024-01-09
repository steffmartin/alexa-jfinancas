SELECT PCPAI.NOME,
       '|',
       PC.NOME,
       '|',
       Cp.HISTORICO,
       '|',
       Cp.VENCIMENTO,
       '|',
       C.NOME,
       '|',
       CLI.NOMEFANTASIA,
       '|',
       CAST(Cp.VALOR AS NUMERIC(18, 2))
FROM CPAGAR cp
         JOIN CLIENTE cli ON cp.ID_CLIENTE = cli.ID
         JOIN conta c ON cp.ID_CONTA = c.ID
         JOIN PLANODECONTAS pc ON cp.ID_PLANCONTA = pc.ID
         JOIN PLANODECONTAS pcpai ON pc.ID_OWNER = PCPAI.ID
         LEFT JOIN TIPOPAGAMENTO tp ON cp.ID_TIPOPGTO = tp.ID
WHERE status = 0
  AND FIMFREQUENCIA = 0
  AND (tp.DESCRICAO IS NULL OR tp.DESCRICAO <> 'Cartão de Crédito')
  AND c.NOME NOT LIKE '%Salário%'
ORDER BY VENCIMENTO;