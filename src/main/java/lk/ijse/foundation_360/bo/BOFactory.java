package lk.ijse.foundation_360.bo;

import lk.ijse.foundation_360.bo.custom.impl.ApprovalBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.ClientBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.ExpenseBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.NotificationBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.OrderBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.ProjectBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.SalaryBOImpl;
import lk.ijse.foundation_360.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory instance;

    private BOFactory() {}

    public static BOFactory getInstance() {
        if (instance == null) instance = new BOFactory();
        return instance;
    }

    public enum BOType {
        CLIENT, EMPLOYEE, PROJECT, ORDER, EXPENSE, NOTIFICATION, USER, SALARY, APPROVAL
    }

    public SuperBO getBO(BOType type) {
        return switch (type) {
            case CLIENT       -> new ClientBOImpl();
            case EMPLOYEE     -> new EmployeeBOImpl();
            case PROJECT      -> new ProjectBOImpl();
            case ORDER        -> new OrderBOImpl();
            case EXPENSE      -> new ExpenseBOImpl();
            case NOTIFICATION -> new NotificationBOImpl();
            case USER         -> new UserBOImpl();
            case SALARY       -> new SalaryBOImpl();
            case APPROVAL     -> new ApprovalBOImpl();
        };
    }
}
