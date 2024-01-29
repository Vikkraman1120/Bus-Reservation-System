import java.util.*;
public class JavaProgram {
    public static void execution() throws Exception{
        Scanner sc = new Scanner(System.in);
        boolean value = true;

        do{
            MainMenu.main_menu();
            System.out.print("Choice : ");
            int choice = sc.nextInt();
            System.out.println("-------------------------------");
            switch (choice) {
                case 1:{
                    boolean logincredit = Login.loginUser();
                    if(logincredit){ 
                        boolean value2 = true;
                        do{
                            MainMenu.sub_menu();
                            System.out.print("Choice : ");
                            int choice2 = sc.nextInt();
                            System.out.println("-------------------------------");
                            switch (choice2) {
                                case 1:
                                    Bus.display_bus_details();
                                    break;
                                case 2:
                                    Reserved.reserved();
                                    break;
                                case 3:
                                    Cancled.cancel();
                                    break;
                                case 4:
                                    value2 = false;
                                    System.out.println("Thankyou! Go to Main menu!");
                                    System.out.println("======================================");
                                    break;
                                default:
                                    break;
                            }
                        }while(value2);
                        System.out.println("======================================");
                    }
                    else
                    {
                        System.out.println("Login failed! Please try again.");
                        System.out.println("======================================");
                    }
                }
                    break;
                case 2:
                    Passanger.signup();
                    System.out.println("======================================");
                    break;
                case 3:
                    value = false;
                    System.out.println("Thankyou! Visit Again!");
                    System.out.println("======================================");
                    break;
                default:
                    break;
            }
        }while(value);
    
    }
}
