-- Salário líquido a receber ( pegar uma descrição para informar se é salario, férias, 13º, plr, etc)
SELECT VENCIMENTO, SUM(VALOR) FROM (
	SELECT VENCIMENTO, VALOR FROM CRECEBER
	JOIN CONTA C1 ON C1.ID = CRECEBER.ID_CONTA 
	WHERE "STATUS" = 0
	AND FIMFREQUENCIA = 0
	AND C1.NOME LIKE '%Salário%'
	UNION
	SELECT VENCIMENTO, -VALOR FROM CPAGAR
	JOIN CONTA C2 ON C2.ID = CPAGAR.ID_CONTA 
	WHERE "STATUS" = 0
	AND FIMFREQUENCIA = 0
	AND C2.NOME LIKE '%Salário%'
)
GROUP BY VENCIMENTO
ORDER BY VENCIMENTO
-- Contas a receber (sem o salário e qualquer estorno programado no cartão)
SELECT PCPAI.NOME, PC.NOME, CR.HISTORICO, CR.VENCIMENTO , CR.VALOR, C.NOME, CLI.NOMEFANTASIA 
FROM CRECEBER cr
JOIN CLIENTE cli ON cr.ID_CLIENTE = cli.ID 
JOIN conta c ON cr.ID_CONTA = c.ID 
JOIN PLANODECONTAS pc ON cr.ID_PLANCONTA = pc.ID 
JOIN PLANODECONTAS pcpai ON pc.ID_OWNER = PCPAI.ID 
LEFT JOIN TIPOPAGAMENTO tp ON cr.ID_TIPOPGTO = tp.ID 
WHERE status = 0
AND FIMFREQUENCIA = 0
AND (tp.DESCRICAO IS NULL OR tp.DESCRICAO  <> 'Cartão de Crédito')
AND c.NOME NOT LIKE '%Salário%'
ORDER BY VENCIMENTO;
-- Contas a pagar (sem os descontos do salário e as conta a pagar com o cartão)
SELECT PCPAI.NOME, PC.NOME, Cp.HISTORICO, Cp.VENCIMENTO , Cp.VALOR, C.NOME, CLI.NOMEFANTASIA 
FROM CPAGAR cp
JOIN CLIENTE cli ON cp.ID_CLIENTE = cli.ID 
JOIN conta c ON cp.ID_CONTA = c.ID 
JOIN PLANODECONTAS pc ON cp.ID_PLANCONTA = pc.ID 
JOIN PLANODECONTAS pcpai ON pc.ID_OWNER = PCPAI.ID 
LEFT JOIN TIPOPAGAMENTO tp ON cp.ID_TIPOPGTO = tp.ID 
WHERE status = 0
AND FIMFREQUENCIA = 0
AND (tp.DESCRICAO IS NULL OR tp.DESCRICAO  <> 'Cartão de Crédito')
AND c.NOME NOT LIKE '%Salário%'
ORDER BY VENCIMENTO;
-- Transferências programadas (cartão de crédito)
SELECT tc.DESCRICAO, c.NOME, tp.VALOR, tp.VENCIMENTO, tp.HISTORICO
FROM TRANSF_PROG tp 
JOIN conta c ON tp.ID_CONTA_TO  = c.ID 
JOIN TIPOCONTA tc ON c.ID_TIPOCONTA = tc.ID 
WHERE status = 0
AND FIMFREQUENCIA = 0
ORDER BY VENCIMENTO;
-- Saldo atual das contas ativas
SELECT tc.DESCRICAO, c.NOME, (SELECT COALESCE(SUM(MC2.VALOR * (CASE WHEN MC2.TIPO IN (1,2) THEN -1 ELSE 1 END)), 0)
          FROM MOVCONTA MC2
         WHERE MC2."DATA" <= CURRENT_DATE 
           AND MC2.STATUS <> 31
           AND MC2.ID_CONTA = C.ID) SALDO
FROM CONTA c 
JOIN TIPOCONTA tc ON c.ID_TIPOCONTA = tc.ID 
WHERE C.INATIVO = 0
ORDER BY tc.DESCRICAO, C.NOME 

-- Extrato corrigido
SELECT MC.ID
     , MC.ID_CONTA
     , MC.TIPO
     , MC.HISTORICO
     , MC.ID_PLANCONTA
     , MC.ID_CCUSTO
     , MC.STATUS
     , MC.ID_CLIENTE
     , MC.FAVORECIDO
     , MC.NUMERO
     , MC.DATA
     , MC.VALOR
     , MC.ID_PLANODEMON
     , MC.GROUPED
     , MC.ID_CONTA_FROM
     , MC.ID_MOVCONTA
     , MC.ID_RECIBO
     , MC.AUTENTICACAO
     , MC.COMPENSACAO
     , MC.OBSERVACOES
     , MC.COMPETENCIA
     , MC.COTACAO
     , MC.CREATEAT
     , MC.UPDATEAT
     , MC.CLASSNAME
     , COALESCE(CASE WHEN MC.TIPO IN (0,3) THEN MC.VALOR ELSE 0 END, 0) CREDITO -- correção: adicionado tipo 3
     , COALESCE(CASE WHEN MC.TIPO IN (1,2) THEN (MC.VALOR*-1) ELSE 0 END, 0) DEBITO -- correção: adicionado tipo 2
     , (SELECT COALESCE(SUM(MC2.VALOR * (CASE WHEN MC2.TIPO IN (1,2) THEN -1 ELSE 1 END)), 0) -- correção: adicionado tipo 2
          FROM MOVCONTA MC2
         WHERE MC2.DATA <= MC.DATA
           AND MC2.STATUS <> 31 -- correção: adicionado status 31
           AND MC2.ID_CONTA = MC.ID_CONTA
           AND MC2.id <= MC.ID) SALDO
 FROM MOVCONTA MC
GROUP BY 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27
ORDER BY 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27;