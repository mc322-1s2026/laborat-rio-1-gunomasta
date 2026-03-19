* [X] Garantir que o Username nao seja Nulo ou composto so de espacos;

  if (username == null || username.isBlank()) {
  throw new IllegalArgumentException("Username não pode ser vazio.");
  }
  this.username = username;
* [ ] Email deve seguir o formato "usuario@dominio.com";
* [x] calculateWorkload() deve mostrar apenas tarefas in progresse e do usuario;
* [X] Email deve seguir o formato "usuario@dominio.com";
* [ ] calculateWorkload() deve mostrar apenas tarefas in progresse e do usuario;
* [ ] Nao permitir que algum metodo/fundao modifique o id;
* [ ] Nao permitir que algum metodo/funcao mude a deadline;
* [X] Checar se existe um user atribuido a tarefa antes de processa-la como in progress else NexusValidationException;
* [X] Ver se uma tarefa nao esta blocked antes de defini-la como done;
* [X] Sempre que alguma transifcao de tarefeas for violada, incrementar contados;
* [ ] Criar uma classe Project em "com.nexus.model", que funcionara como a nova unidade de agrupamento do sistema = portifolio;

  - Atributos:

    -Nome, list(task) e um total budget (em horas);

    -Cada task deve ter um campo "estimatedEffort";

    -O project dvee ter um addTask(task t), que valide se a soma das horas estimadas de cada tarefa + nova nao excede o totalBudget do projeto. Else NexusValidationException;
* [ ] Gerar relatorios usando a Stream API:

- [ ] Metodo que retorna os 3 usuarios que possuem maior numero de tarefas feitas;
- [ ] Lista usuarios com mais de 10 tarefas in_progress;
- [ ] Para um dado projeto (parametro), calcular o percentual de conclusao dele;
- [ ] Identificar qual status possui maior numero absoluto de tarefas (exceto done);

-Refatorar o logProcessor para que consiga interpretar 6 novos codigos, e dar NexusValidadtionException caso nao exista o codigo ou que o change_status viole uma regra (apos incrementar o contador global de erros);

-`COMANDO;PARAMETRO1;PARAMETRO2...`

Você deve implementar o suporte aos seguintes comandos:

1. **`CREATE_USER;username;email`**: Instancia um novo usuário (valide o e-mail!).
2. **`CREATE_PROJECT;projectName;budgetHours`**: Instancia um novo projeto.
3. **`CREATE_TASK;taskName;deadline;effort;projectName`**: Cria uma tarefa, define seu esforço e a vincula automaticamente ao projeto mencionado.
4. **`ASSIGN_USER;taskId;username`**: Localiza a tarefa pelo ID e o usuário pelo username, realizando a atribuição (Owner).
5. **`CHANGE_STATUS;taskId;newStatus`**: Tenta mover a tarefa para um novo estado (ex: `IN_PROGRESS`, `DONE`, `BLOCKED`). **Atenção**: Este comando deve disparar todas as validações de máquina de estado criadas na Seção 4.
6. **`REPORT_STATUS`**: Aciona a impressão dos relatórios analíticos (Streams) no console.
