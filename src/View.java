import java.util.Scanner;

public class View {
    public static void menu(){
        System.out.println("==========================");
        System.out.println("1. Add new course");
        System.out.println("2. List all courses");
        System.out.println("3. Get course by ID");
        System.out.println("4. Exit");
        System.out.println("==========================");
    }
    public static void main(String[] args) {
        CourseService course = new CourseServiceImp();
        menu();
        while (true) {
            System.out.print("+ Choose your option: ");
            int op = new Scanner(System.in).nextInt();
            switch (op) {
                case 1 -> course.addNewCourse();
                case 2 -> course.getAllCourse();
                case 3 -> course.getCourseByid();
                case 4 -> System.exit(0);
                default -> System.out.println("Don't have this option");
            }
        }
    }
}
