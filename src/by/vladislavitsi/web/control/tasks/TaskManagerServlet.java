package by.vladislavitsi.web.control.tasks;

import by.vladislavitsi.web.control.app.ApplicationContext;
import by.vladislavitsi.web.control.app.AbstractPostOnlyServlet;
import by.vladislavitsi.web.control.exceptions.DAOException;
import by.vladislavitsi.web.model.task.Task;
import by.vladislavitsi.web.model.user.User;
import by.vladislavitsi.web.util.ICleaner;
import by.vladislavitsi.web.util.impl.Cleaner;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static by.vladislavitsi.web.util.Constants.*;

public class TaskManagerServlet extends AbstractPostOnlyServlet {

    @Override
    protected void performRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        try {
            Action.valueOf(request.getParameter(ATTRIBUTE_ACTION).toUpperCase()).performRequest(request, response);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private enum Action{
        GET_TASKS {
            @Override
            void performRequest(HttpServletRequest request,
                                HttpServletResponse response) throws DAOException, IOException {
                List<Task> tasks = ApplicationContext.getTaskDAOImpl().getTasks(getUserId(request));
                String json = new Gson().toJson(tasks);
                try(PrintWriter printWriter = response.getWriter()) {
                    printWriter.write(json);
                    printWriter.flush();
                }
            }
        },
        SAVE_TASK {
            @Override
            void performRequest(HttpServletRequest request, HttpServletResponse response) throws DAOException {
                String taskJson = request.getParameter(ATTRIBUTE_TASK_JSON);
                Task task = new Gson().fromJson(taskJson, Task.class);
                if (task.getId()<=0){
                    ApplicationContext.getTaskDAOImpl().addTask(getUserId(request), task);
                }else {
                    ApplicationContext.getTaskDAOImpl().editTask(getUserId(request), task);
                }
            }
        },
        DONE_TASK {
            @Override
            void performRequest(HttpServletRequest request, HttpServletResponse response) throws DAOException {
                int taskId = Integer.parseInt(request.getParameter(ATTRIBUTE_TASK_ID));
                ApplicationContext.getTaskDAOImpl().doneTask(getUserId(request), taskId);
            }
        },
        BIN_TASK {
            @Override
            void performRequest(HttpServletRequest request, HttpServletResponse response) throws DAOException {
                int taskId = Integer.parseInt(request.getParameter(ATTRIBUTE_TASK_ID));
                ApplicationContext.getTaskDAOImpl().binTask(getUserId(request), taskId);
            }
        },
        DELETE_TASK {
            @Override
            void performRequest(HttpServletRequest request,
                                HttpServletResponse response) throws DAOException, ServletException, IOException {
                int taskId = Integer.parseInt(request.getParameter(ATTRIBUTE_TASK_ID));
                User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
                ICleaner cleaner = new Cleaner();
                cleaner.cleanTasksAndFiles(user, taskId);
            }
        },
        EMPTY_BIN {
            @Override
            void performRequest(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
                User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
                List<Integer> taskIds = ApplicationContext.getTaskDAOImpl().getTasksToDelete(Integer.parseInt(user.getId()));
                ICleaner cleaner = new Cleaner();
                cleaner.cleanTasksAndFiles(user, taskIds);
            }
        };
        abstract void performRequest(HttpServletRequest request,
                                     HttpServletResponse response) throws DAOException, IOException, ServletException;
        protected int getUserId(HttpServletRequest request){
            return Integer.parseInt(((User)request.getSession().getAttribute(ATTRIBUTE_USER)).getId());
        }
    }


}
