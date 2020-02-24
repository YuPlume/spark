package com.ibeifeng.sparkproject.test;

public class Singleton {
    public static Singleton instance = null;


    /*
    * 必须保证自己的构造方法使用private私有化
    * 这样才能保证，外界的代码不能随意创建类的实例
    * */
    private Singleton(){

    }


//  最后需要有一个共有的静态方法
//    这个方法负责创建唯一的实例，并返回唯一的实例
    public static Singleton getInstance() {
        /*
        * 两步检查机制
        * 首先第一步，多个线程过来的时候，判断instance是否为null
        * 如果为null，再往下走
        * */
            if (instance==null){
                /*
                * 在这里进行多个线程的同步
                * 同一时间，只能有一个线程获取到Singleton class的对象锁
                * 进入后续的代码
                * 其他线程都是只能够在原地等待，获取锁
                * */
                synchronized (Singleton.class){
                    /*
                    * 只有第一个获取到锁的线程，进入到这里，会发现instance是null，
                    * 然后才会去创建一个单例
                    * 此后，线程哪怕是走到这一步，也发现instance已经不是null了，
                    * 就不会反复创建一个单例了
                    * */
                    if (instance==null){
                        instance=new Singleton();
                    }
                }
            }
        return instance;
    }

}
