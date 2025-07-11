package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTaskSemDescricao() {
        try {
            controller.save(setTask("", LocalDate.now()));
            Assert.fail("Não deveria chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskComDescricaoNull() {
        try {
            controller.save(setTask(null, LocalDate.now()));
            Assert.fail("Não deveria chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskSemData() {
        try {
            controller.save(setTask("Descrição", null));
            Assert.fail("Não deveria chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskComDataPassada() {
        try {
            controller.save(setTask("Descrição", LocalDate.now().minusDays(1)));
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskComDataFutura() {
        try {
            controller.save(setTask("Descrição", LocalDate.now().plusDays(1)));
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTaskComSucesso() {
        Task todo = setTask("Descrição", LocalDate.now());
        taskRepo.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }

    public Task setTask(String description, LocalDate dueDate) {
        Task todo = new Task();
        todo.setTask(description);
        todo.setDueDate(dueDate);
        return todo;
    }
}
