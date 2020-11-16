import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskItem implements Serializable {

    private String title;
    private String description;
    private boolean complete;
    private LocalDate dueDate;


    public TaskItem(String title, String description, LocalDate dueDate) {

        this.setTitle(title);
        this.setDescription(description);
        this.complete = false;
        this.setDueDate(dueDate);
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) throws NullPointerException {
        if (title.trim().equals("") || title == null) {
            throw new NullPointerException("Title needs to be at least one Character");
        }
        this.title = title.trim();
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String project) {

        this.description = project.trim();
    }

    public boolean isComplete() {

        return this.complete;
    }

    public boolean markIncomplete() {
        this.complete = false;
        return this.complete;
    }

    public boolean markCompleted() {
        this.complete = true;
        return this.complete;
    }

    public LocalDate getDueDate() {

        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) throws DateTimeException {
        if (dueDate.compareTo(LocalDate.now())<0)
        {
            throw new DateTimeException("Needs to be a Date in the future");
        }
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dueDate = LocalDate.parse(dueDate.format(formattedDate));
    }

    public String formattedStringOfTask() {
        return (
                "\nTitle     : " + title + "\nProject   : " + description + "\nStatus    : " + (complete?"Completed":"NOT COMPLETED") + "\nDue Date  : " + dueDate + "\n");
    }

}

