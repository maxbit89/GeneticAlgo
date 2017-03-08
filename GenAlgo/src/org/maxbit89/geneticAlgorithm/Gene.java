package org.maxbit89.geneticAlgorithm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Gene {
	Class<? extends Mutator> mutator();
}
