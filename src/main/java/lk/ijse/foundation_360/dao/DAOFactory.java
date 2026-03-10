package lk.ijse.foundation_360.dao;

import lk.ijse.foundation_360.dao.custom.impl.ApprovalDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.ClientDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.ExpenseDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.NotificationDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.OrderDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.ProjectDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.foundation_360.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory instance;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (instance == null) instance = new DAOFactory();
        return instance;
    }

    public enum DAOType {
        CLIENT, EMPLOYEE, PROJECT, ORDER, EXPENSE, NOTIFICATION, USER, SALARY, APPROVAL
    }

    public SuperDAO getDAO(DAOType type) {
        return switch (type) {
            case CLIENT      -> new ClientDAOImpl();
            case EMPLOYEE    -> new EmployeeDAOImpl();
            case PROJECT     -> new ProjectDAOImpl();
            case ORDER       -> new OrderDAOImpl();
            case EXPENSE     -> new ExpenseDAOImpl();
            case NOTIFICATION-> new NotificationDAOImpl();
            case USER        -> new UserDAOImpl();
            case SALARY      -> new SalaryDAOImpl();
            case APPROVAL    -> new ApprovalDAOImpl();
        };
    }
}
