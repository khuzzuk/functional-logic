package pl.khuzzuk.functions;

import java.util.Objects;
import java.util.function.Predicate;

public class Validators
{
   private static final Predicate TRUE = __ -> true;
   private static final Predicate NULL_SAFE = Objects::nonNull;

   @SuppressWarnings("unchecked")
   public static <T> Predicate<T> alwaysTrue()
   {
      return TRUE;
   }

   @SuppressWarnings("unchecked")
   public static <T> Predicate<T> nullSafe()
   {
      return NULL_SAFE;
   }
}
