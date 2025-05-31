module oops_project {
    requires javafx.controls;
    requires javafx.fxml;

    opens oops_project.controller to javafx.fxml;
    exports oops_project;
}
