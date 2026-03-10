module lk.ijse.foundation_360 {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires java.base;
    requires transitive java.sql;
    requires java.logging;

    opens lk.ijse.foundation_360 to javafx.fxml;
    opens lk.ijse.foundation_360.controller to javafx.fxml;
    opens lk.ijse.foundation_360.dto to javafx.base, javafx.fxml;
    opens lk.ijse.foundation_360.entity to javafx.base, javafx.fxml;
    opens lk.ijse.foundation_360.db to javafx.fxml;
    opens lk.ijse.foundation_360.util to javafx.fxml;
    opens lk.ijse.foundation_360.bo to javafx.fxml;
    opens lk.ijse.foundation_360.bo.custom to javafx.fxml;
    opens lk.ijse.foundation_360.bo.custom.impl to javafx.fxml;
    opens lk.ijse.foundation_360.dao to javafx.fxml;
    opens lk.ijse.foundation_360.dao.custom to javafx.fxml;
    opens lk.ijse.foundation_360.dao.custom.impl to javafx.fxml;

    exports lk.ijse.foundation_360;
    exports lk.ijse.foundation_360.controller;
    exports lk.ijse.foundation_360.bo;
    exports lk.ijse.foundation_360.bo.custom;
    exports lk.ijse.foundation_360.bo.custom.impl;
    exports lk.ijse.foundation_360.dao;
    exports lk.ijse.foundation_360.dao.custom;
    exports lk.ijse.foundation_360.dao.custom.impl;
    exports lk.ijse.foundation_360.dto;
    exports lk.ijse.foundation_360.entity;
    exports lk.ijse.foundation_360.db;
    exports lk.ijse.foundation_360.util;
}
