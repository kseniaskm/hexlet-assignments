package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
       for (Method method: Address.class.getDeclaredMethods()) {
           if (method.isAnnotationPresent(Inspect.class)) {
               if (method.getReturnType() == String.class) {
                   System.out.println("Method " + method.getName() + " returns a value of type String");
               } else
               System.out.println("Method " + method.getName() + " returns a value of type " + method.getReturnType());
           }
       }
        // END
    }
}
