> **Disclaimer**: Esse projeto foi descontinuado antes de sua conclusão devido a limitações de tempo e dedicação a outras iniciativas. Mesmo assim, ele permanece público para servir como uma referência e fonte de aprendizado para outros desenvolvedores. Sinta-se à vontade para explorar o código.

# Alexa jFinanças

![](assets/banner.png)

O projeto "Alexa jFinanças" é uma aplicação pessoal desenvolvida para facilitar o acesso e controle das finanças
pessoais por meio da integração entre o software de finanças pessoais jFinanças Pessoal 2015 e a assistente virtual
Alexa da Amazon.

## Visão Geral

O projeto consiste em dois módulos:

### Módulo Local (jFinanças Alexa Sync):

- Responsável por sincronizar os dados financeiros locais, incluindo saldos e informações sobre contas a pagar e a
  receber, com a nuvem.
- Utiliza o software de finanças pessoais jFinanças Pessoal 2015 para extrair e enviar esses dados para a nuvem de forma
  segura.
- Facilita a atualização em tempo real das informações financeiras, garantindo que estejam prontamente disponíveis para
  a assistente virtual.

### Módulo Cloud (jFinanças Alexa Skill):

- Este módulo serve como backend para a skill da Alexa, permitindo que a assistente virtual consulte dados específicos,
  como saldos de contas e contas vencidas.
- Fornece respostas personalizadas, tornando a interação do usuário com suas finanças mais intuitiva e acessível.
- Garante a segurança e a integridade dos dados pessoais do usuário utilizando um identificador de voz único para
  identificar o dono da conta.

## Objetivo

O objetivo principal deste projeto é proporcionar uma experiência integrada e personalizada no controle das finanças
pessoais, oferecendo ao usuário a conveniência de acessar informações financeiras de maneira rápida e intuitiva por meio
da assistente virtual Alexa.

<blockquote>
Este projeto é desenvolvido exclusivamente para uso pessoal e não tem intenções comerciais. Todas as informações financeiras são tratadas com segurança e privacidade, mantendo o controle total nas mãos do usuário.
</blockquote>

## Utilização

### Pré-requisitos

Antes de começar a utilizar a aplicação, certifique-se de atender aos seguintes pré-requisitos:

1. Software jFinanças Pessoal 2015:
    - Possuir o jFinanças Pessoal 2015 instalado e ativado com uma licença válida.
2. Conta na AWS:
    - Possuir uma conta na Amazon Web Services (AWS) para hospedagem de recursos (o *tier* gratuito é suficiente).
3. Conta na Amazon Developer:
    - Possuir uma conta na plataforma Amazon Developer para configurar a Skill da Alexa.
    - Esta conta deve ser a mesma utilizada no seu dispositivo Alexa.
4. AWS CLI:
    - Instalar a AWS Command Line Interface (AWS CLI) para facilitar a interação com serviços AWS.
5. Firebird 2.5:
    - Instalar o Firebird 2.5, que é utilizado para a acesso seguro aos dados financeiros do jFinanças Pessoal 2015.
6. Terraform:
    - Instalar o Terraform para facilitar a criação da infraestrutura como código (IaC) na AWS.
7. Java 20 ou superior:
    - É necessário possuir o Java Development Kit (JDK) em versão 20 ou superior para compilação e execução da aplicação.
8. Maven 3 ou superior:
    - Ter o Maven 3 ou superior para compilação do projeto.

### Instalação

#### Etapa 1: Clonar este projeto

1. Clone o repositório:
    ```bash
    git clone https://github.com/steffmartin/alexa-jfinancas.git
    ```

#### Etapa 2: Infraestrutura como Código (IaaC) com Terraform

1. Navegue até a pasta `infra` do projeto:
    - Abra o terminal ou prompt de comando nesta pasta.
2. Execute os comandos Terraform:
    ```bash
    terraform init
    ```
    ```bash
    terraform apply
    ```
    - O Terraform exibirá os recursos a serem criados, solicitará confirmação e então provisionará a infraestrutura
      necessária na AWS.
    - Se este comando não funcionar, verifique a configuração e os acessos do AWS CLI.
3. Armazene as credenciais nas variáveis de ambiente:
    - Abra o arquivo `terraform.tfstate` e localize os *outputs*.
    - Abra as Variáveis de Ambiente do seu usuário ou sistema.
        - Pressione `Win + S`, digite "Variáveis de Ambiente" e pressione Enter.
    - Crie uma variável chamada `JFSYNC_AWS_ACCESS_KEY_ID` e preencha com o valor do *output* `jfin_sync_access_key_id`.
    - Crie uma variável chamada `JFSYNC_AWS_SECRET_ACCESS_KEY` e preencha com o valor do
      *output* `jfin_sync_secret_access_key`.

#### Etapa 2: Compilação do Aplicativo Local

1. Navegue até a Pasta `jFinancas Alexa Sync` do projeto:
    - Abra o terminal ou prompt de comando nesta pasta.
2. Compile o projeto com o Maven:
    ```bash
    mvn clean install
    ```
    - O Maven compilará o projeto e resolverá as dependências necessárias.
    - A pasta `target` será criada com o arquivo `.jar` da aplicação.

#### Etapa 3: Configuração do Aplicativo Local

A aplicação local utiliza variáveis de ambiente para configurar suas operações. Abaixo está a lista das variáveis de
ambiente relevantes, juntamente com valores padrão, caso as variáveis não estejam definidas. Certifique-se de ajustar as
variáveis conforme necessário:

| Variável de Ambiente         | Valor Padrão                                                     | Descrição                                                                                                                 |
|------------------------------|------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| JFSYNC_DBUSER                | `SYSDBA`                                                         | Nome de usuário da base de dados do jFinanças Pessoal 2015                                                                |
| JFSYNC_DBPASSWORD            | `masterkey`                                                      | Senha da base de dados do jFinanças Pessoal 2015                                                                          |
| JFSYNC_DBPATH                | `%LOCALAPPDATA%\Cenize\jFinanças Pessoal 2015\jfinancas.jfin`    | Caminho completo para o arquivo de banco de dados do jFinanças Pessoal 2015                                               |
| JFSYNC_ALEXA_USER_ID         |                                                                  | Seu identificador de voz ou de usuário da Alexa (veja Etapa 7)                                                            |
| JFSYNC_AWS_REGION            | Se não fornecido, lê a variável AWS_REGION ou AWS_DEFAULT_REGION | Região da AWS                                                                                                             |
| JFSYNC_AWS_ACCESS_KEY_ID     | Se não fornecido, lê a variável AWS_ACCESS_KEY_ID                | Usuário da AWS (veja Etapa 2)                                                                                             |
| JFSYNC_AWS_SECRET_ACCESS_KEY | Se não fornecido, lê a variável AWS_SECRET_ACCESS_KEY            | Senha da AWS (veja Etapa 2)                                                                                               |
| JFSYNC_TIPO_CTA_CARTAO       | `Cartão de Crédito`                                              | Descrição do "Tipo de Conta" referente ao grupo de cartões de crédito                                                     |       
| JFSYNC_TIPO_CTA_SALARIO      | `Salário`                                                        | Descrição do "Tipo de Conta" referente ao grupo de contas salário                                                         |
| JFSYNC_APENAS_PG_INI         | `false`                                                          | Se `true` somente as contas exibidas na página inicial do jFinanças Pessoal 2015 serão sincronizadas com a Alexa          |
| JFSYNC_APENAS_PREV_FIN       | `false`                                                          | Se `true` somente as contas incluídas nas previsões financeiras do jFinanças Pessoal 2015 serão sincronizadas com a Alexa |
| JFSYNC_APENAS_CTA_MOV        | `false`                                                          | Se `true` somente as contas do tipo "movimentação" do jFinanças Pessoal 2015 serão sincronizadas com a Alexa              |

#### Etapa 4: Configuração do Windows para Execução Local

1. Ativação da Auditoria de Acompanhamento de Processos:
    - Abra o Editor de Política de Grupo Local:
        - Pressione `Win + R`, digite `gpedit.msc` e pressione Enter.
    - Navegue até Configuração do Computador → Configurações do Windows → Configurações de Segurança → Políticas
      Locais → Política de Auditoria.
    - No painel direito, clique duas vezes em "Auditoria de acompanhamento de Processos" e marque o campo "Êxito".
    - Clique em `OK` para aplicar as alterações.
3. Ajuste o modelo de Tarefa Agendada:
    - Abra o arquivo `jFinancasAlexaSyncTask.xml` dentro da pasta `assets` do projeto com o Bloco de Notas ou outro
      editor de texto.
    - No final da linha **12**, localize e corrija o caminho do executável do jFinanças Pessoal 2015.
    - Na linha **44**, corrija o caminho da pasta `target` deste projeto.
    - Salve as alterações.
3. Importação da Tarefa Agendada:
    - Abra o Agendador de Tarefas:
        - Pressione `Win + S`, digite "Agendador de Tarefas" e pressione Enter.
    - No painel de ações à direita, clique em `Importar Tarefa...`
    - Navegue até o diretório `assets` no repositório do projeto e selecione o arquivo `jFinancasAlexaSyncTask.xml`.
    - Clique em `OK` para importar a tarefa agendada.

<blockquote>
Este projeto ainda está em fase de desenvolvimento, e as instruções a seguir estão em processo de definição.
<br><br>
Até o momento, você já configurou um sistema local que sincroniza seus dados financeiros com a AWS, ele deve ser executado sempre que o jFinanças Pessoal 2015 for fechado.
<br><br>
Caso não observe qualquer notificação relacionada à execução desta sincronização, por favor, revise cuidadosamente os passos anteriores para garantir uma configuração correta.
</blockquote>

#### Etapa 5: Criação da Skill da Alexa

Em breve...

#### Etapa 6: Instalação da Skill na sua Alexa

Em breve...
