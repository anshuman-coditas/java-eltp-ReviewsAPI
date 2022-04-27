package com.example.demo.service;

import com.example.demo.repository.ReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;
    @Autowired
    private SessionFactory sf;

   public List find(float a,float c,float f,float d,float se ){
        Session s=sf.openSession();
        Query q=s.createQuery("from Review where ambience>:x and clean > :y " +
                "and food > :z and drinks > :d and service > :s");
        q.setParameter("x",a);
       q.setParameter("y",c);
       q.setParameter("z",f);
       q.setParameter("d",d);
       q.setParameter("s",se);
        return q.list();
    }
    public List ambienceAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG(ambience) from Review");
        return  q.list();
    }
    public List foodAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG(food) from Review");
        return  q.list();
    }
    public List cleanAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG(clean) from Review");
        return  q.list();
    }
    public List drinksAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG(drinks) from Review");
        return  q.list();
    }
    public List serviceAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG(service) from Review");
        return  q.list();
    }
    public List overAllAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG((ambience+food+clean+drinks+food+service)/5) from Review");
        return  q.list();
    }

}
