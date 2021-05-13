package com.chaos.core.demo;

import lombok.Data;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 理解ThreadLocal的工作原理，思考 工作场景
 *
 * @author huangdawei 2020/8/16
 */
public class ThreadLocalTest01 {

    public static void main(String[] args) {

        ThreadLocal<Person> local = new ThreadLocal<>();
        ThreadLocalTest01 test01 = new ThreadLocalTest01();
        Person person = test01.new Person("王一", 18, "九幽");
        local.set(person);

        IntStream.range(0, 5).forEach(p -> new Thread(() -> {

            Person personThread = local.get();
            if (Objects.isNull(personThread)) {
                System.out.println("thread" + p + " set  person,person:" + person.toString());
                local.set(person);
                personThread = local.get();
            }
            personThread.setAge(personThread.getAge() + p);

            System.out.println("thread" + p + " personThread:" + personThread.toString());
            System.out.println("thread" + p + " person:" + person.toString());
            System.out.println("personThread equals person:" + Objects.equals(personThread, person));
            System.out.println("personThread == person:" + (personThread == person));
            System.out.println();

            try {
                TimeUnit.SECONDS.sleep(1);
                local.remove();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }

    @Data
    class Person {
        private String name;
        private Integer age;
        private String address;

        public Person(String name, Integer age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }
    }
}
