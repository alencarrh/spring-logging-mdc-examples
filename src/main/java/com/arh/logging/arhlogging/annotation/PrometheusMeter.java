package com.arh.logging.arhlogging.annotation;

public @interface PrometheusMeter {



  boolean success2xx() default true;

  boolean success3xx() default false;

  boolean success4xx() default false;

  boolean success5xx() default false;

  //  Class<? extends Consumer<Object>> handler() default DefaultHandler.class;
  //
  //  class DefaultHandler implements Consumer<Object> {
  //    @Override
  //    public void accept(Object o) {}
  //  }
}
