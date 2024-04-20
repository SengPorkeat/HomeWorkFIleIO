import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

interface CourseService{
    void addNewCourse();
    void getAllCourse();
    void getCourseByid();
}
class CourseServiceImp implements CourseService {
    @Override
    public void addNewCourse() {
        Random random = new Random();
        LocalDate startdate = LocalDate.now();
        LocalDate endDate = startdate.plusDays(1).plusMonths(1);
        System.out.print("Insert TITLE: ");
        String title = new Scanner(System.in).nextLine();
        Course course = new Course(
                random.nextInt(100),
                title,
                startdate.toString(),
                endDate.toString(),
                true
        );
        try {
            FileOutputStream output = new FileOutputStream("course.csv", true);
            String content = course.getCourseid() + ","
                    + course.getCourseTitle() + ","
                    + course.getCourseStartedDate() + ","
                    + course.getCourseEndedDate() + ","
                    + course.getIsAvailable() + "\n";
            output.write(content.getBytes());
            output.close();
        } catch (Exception ignoreA) {}
    }
    @Override
    public void getAllCourse() {
        try {
            BufferedReader readerbuffer = new BufferedReader(new FileReader("course.csv"));
            String data;
            String[] contents;
            List<Course> courses = new ArrayList<>();
            while ((data = readerbuffer.readLine()) != null) {
                contents = data.split(",");
                courses.add(new Course(
                        Integer.valueOf(contents[0].trim()),
                        contents[1],
                        contents[2],
                        contents[3],
                        Boolean.parseBoolean(contents[4].trim())));
            }
            Table table = new Table(5,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.ALL);
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 15, 15);
            }
            table.addCell("Course ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Course Title", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Started Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Ended Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Available", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            for (Course course : courses) {
                table.addCell(String.valueOf(course.getCourseid()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getCourseTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getCourseStartedDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(course.getCourseEndedDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(String.valueOf(course.getIsAvailable()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }
            System.out.println(table.render());
            readerbuffer.close();
        } catch (Exception ignoreB) {}
    }
    @Override
    public void getCourseByid() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("course.csv"));
            System.out.print("Enter the course ID: ");
            int searchId = new Scanner(System.in).nextInt();
            boolean found = false;
            Table table = new Table(5,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.ALL);
            for (int i = 0; i < 5; i++) {
                table.setColumnWidth(i, 15, 15);
            }
            table.addCell("Course ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Course Title", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Started Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Ended Date", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Available", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            String data;
            String[] contents;
            while ((data = reader.readLine()) != null) {
                contents = data.split(",");
                int courseId = Integer.parseInt(contents[0].trim());
                if (courseId == searchId) {
                    table.addCell(contents[0], new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(contents[1], new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(contents[2], new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(contents[3], new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(contents[4], new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Not found course with ID:" + searchId);
            } else {
                System.out.println(table.render());
            }
        } catch (Exception ignoreC) {}
    }
}