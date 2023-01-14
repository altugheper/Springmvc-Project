package com.tpe.repository;

import com.tpe.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository // @Component'i da iceriyor, Sprint Framework e bu classimin repo katmani oldugunu soylemis oluyor
public class StudentRepositoryImpl implements StudentRepository{


    @Autowired // db katmaninda oldugum icin SF bagimliligimi enjekte ettim
    private SessionFactory sessionFactory;

    @Override
    public List<Student> getAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<Student> listOfStudents =
                session.createQuery("FROM Student",Student.class).getResultList();

        tx.commit();
        session.close();
        return listOfStudents;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Student student = session.get(Student.class,id);
        Optional<Student> opt;
        opt = Optional.ofNullable(student); //OfNullable: Eger donen deger null ise
                                            // ici bos bir student objesi olusturup dondurur. NullPointerException almaktan kurtarir

        tx.commit();
        session.close();
        return opt;
    }

    @Override
    public void save(Student student) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(student); //saveOrUpdate methodu: dbde bu object varsa update et, yoksa create et, NullPointerException almamak icin kullaniyorum

        tx.commit();
        session.close();

    }

    @Override
    public void update(Student student) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(student);

        tx.commit();
        session.close();

    }


    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Student student = session.load(Student.class,id); //Proxy object geldigi icin database i yormuyor, buradaki id silinecegi icin load methodu o yuzden daha mantikli
        session.delete(student);

        tx.commit();
        session.close();
    }
}
