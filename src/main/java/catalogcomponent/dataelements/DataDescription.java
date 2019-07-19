package catalogcomponent.dataelements;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataDescription {

    String[] columnNames();

}
