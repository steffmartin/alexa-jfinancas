@echo off
chcp 65001 > nul

set ISQL_PATH="C:\Program Files\Firebird\Firebird_2_5\bin\isql.exe"
set DB_PATH="C:\Users\steff\AppData\Local\Cenize\jFinanças Pessoal 2015\jfinancas.jfin"
set USER=SYSDBA
set PASSWORD=masterkey
set CHARSET="UTF8"
set ISQL=%ISQL_PATH% -user %USER% -password %PASSWORD% %DB_PATH% -ch %CHARSET%

%ISQL% -i "sql\saldos.sql" -o "results\saldos.txt"
%ISQL% -i "sql\salario_a_receber.sql" -o "results\salario_a_receber.txt"
%ISQL% -i "sql\contas_a_receber.sql" -o "results\contas_a_receber.txt"
%ISQL% -i "sql\contas_a_pagar.sql" -o "results\contas_a_pagar.txt"
%ISQL% -i "sql\transferencias_programadas.sql" -o "results\transferencias_programadas.txt"

rem tratando os resultados

more +3 "results\saldos.txt" > "results\saldos2.txt"
more +3  "results\salario_a_receber.txt" > "results\salario_a_receber2.txt"
more +3  "results\contas_a_receber.txt" > "results\contas_a_receber2.txt"
more +3  "results\contas_a_pagar.txt" > "results\contas_a_pagar2.txt"
more +3  "results\transferencias_programadas.txt" > "results\transferencias_programadas2.txt"

rem excluindo arquivos temporários
pause
del /Q "results\*"