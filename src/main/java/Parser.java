public class Parser {

    private TaskList taskList;

    public Parser(TaskList taskList){
        this.taskList = taskList;
    }

    public void runCommand(Command command) throws DukeException, Exception{
        Task task;
        switch (command) {
            case BYE:
                Ui.print("Bye. Hope to see you again soon!");
                break;
            case LIST:
                Ui.printList(taskList.getTodoList());
                break;
            case TODO:
                task = parseTodoTask(command.getTaskContent());
                Ui.printTodoTask(task.toString(), taskList.getSize(), taskList.getUndoneCount());
                break;
            case DEADLINE:
                task = parseDeadlineTask(command.getTaskContent());
                Ui.printDeadlineTask(task.toString(), taskList.getSize(), taskList.getUndoneCount());
                break;
            case EVENT:
                task = parseEventTask(command.getTaskContent());
                Ui.printEventTask(task.toString(), taskList.getSize(), taskList.getUndoneCount());
                break;
            case DONE:
                task = parseDone(command.getTaskContent());
                Ui.printDelete(task, taskList.getUndoneCount());
                break;
            case DELETE:
                task = parseDelete(command.getTaskContent());
                Ui.printDelete(task, taskList.getUndoneCount());
                break;
            case INVALID:
                throw new CommandException(command.echo() + " is an invalid command.\n"+
                        "please try another one.");
            default:
                throw new DukeException("This default situation should not happen."
                        + "Please contact the idiot programmer.");

        }
    }

    public Task parseTodoTask(String content) throws EmptyDescriptionException{
        if (content.length() == 0)
            throw new EmptyDescriptionException("TODO");
        return taskList.addTodoTask(content);
    }

    public Task parseDeadlineTask(String content) throws EmptyDescriptionException, ParseErrorException, WrongDescriptionException{
        if (content.length() <= 0)
            throw new EmptyDescriptionException("DEADLINE");
        try {
            String[] splitedContent = content.split("/by");
            return taskList.addDeadlineTask(splitedContent[0], splitedContent[1]);
        } catch (ParseErrorException e){
            throw e;
        } catch (Exception e){
            throw new WrongDescriptionException("The format of 'DEADLINE' is:\n"
                    + "> deadline TASK /by TIME\n"
                    + "Please re-enter your command.");
        }
    }

    public Task parseEventTask(String content) throws EmptyDescriptionException, ParseErrorException, WrongDescriptionException{
        if (content.length() <= 0)
            throw new EmptyDescriptionException("EVENT");
        try {
            String[] splitedContent = content.split("/at");
            return taskList.addEventTask(splitedContent[0], splitedContent[1]);
        } catch (ParseErrorException e){
            throw e;
        } catch (Exception e){
            throw new WrongDescriptionException("The format of 'EVENT' is:\n"
                    + "> event TASK /at TIME /to TIME\n"
                    + "Please re-enter your command.");
        }
    }

    public Task parseDone(String content) throws DukeException, WrongDescriptionException, EmptyDescriptionException{
        try {
            if (content.length() < 1)
                throw new EmptyDescriptionException("DONE");
            int index = Integer.parseInt(content) - 1;
            return taskList.doneTask(index);
        } catch (IndexOutOfBoundsException e){
            throw new DukeException("Sorry, I can't do that for you.\n"+
                    "You only have " + String.format("%d",taskList.getSize()) + " tasks on your list.");
        } catch (Exception e) {
            throw new WrongDescriptionException("The description of 'DONE' should be an integer.\n"
                    + "Please re-enter your command.");
        }
    }

    public Task parseDelete(String content) throws DukeException, WrongDescriptionException, EmptyDescriptionException{
        try {
            if (content.length() < 1)
                throw new EmptyDescriptionException("DELETE");
            int index = Integer.parseInt(content) - 1;
            return taskList.deleteTask(index);
        } catch (IndexOutOfBoundsException e){
            throw new DukeException("Sorry, I can't do that for you.\n"+
                    "You only have " + String.format("%d",taskList.getSize()) + " tasks on your list.");
        } catch (Exception e) {
            throw new WrongDescriptionException("The description of 'DELETE' should be an integer.\n"
                    + "Please re-enter your command.");
        }
    }
}