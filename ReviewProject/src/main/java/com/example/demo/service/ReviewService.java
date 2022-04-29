package com.example.demo.service;

import com.example.demo.repository.ReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;
    @Autowired
    private SessionFactory sf;

   public List find(float a,float c,float f,float d,float se ){
        Session s=sf.openSession();
        Query q=s.createQuery("SELECT u.username,r.ambience,r.clean,r.food,r.drinks,r.service from Review r INNER JOIN User u on r.user.id=u.id where ambience>:x and clean > :y " +
                "and food > :z and drinks > :d and service > :s");
        q.setParameter("x",a);
       q.setParameter("y",c);
       q.setParameter("z",f);
       q.setParameter("d",d);
       q.setParameter("s",se);
        return q.list();
    }
    public Map ambienceAvg(){
        Session s=sf.openSession();
        Query q=s.createQuery("select AVG(ambience) from Review");
        Query q1=s.createQuery("select AVG(food) from Review");
        Query q2=s.createQuery("select AVG(clean) from Review");
        Query q3=s.createQuery("select AVG(drinks) from Review");
        Query q4=s.createQuery("select AVG(service) from Review");
        Query q5=s.createQuery("select AVG((ambience+food+clean+drinks+food+service)/5) from Review");
        Map m=new HashMap();
        m.put("ambience",q.list());
       m.put("food",q1.list());
       m.put("clean",q2.list());
       m.put("drinks",q3.list());
       m.put("service",q4.list());
       m.put("overall",q5.list());
       return  m;

    }


}
