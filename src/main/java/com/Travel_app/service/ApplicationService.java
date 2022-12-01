package com.Travel_app.service;

import com.Travel_app.db.model.Application;
import com.Travel_app.db.model.Tip;
import com.Travel_app.db.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class ApplicationService {
    @Autowired
    ApplicationRepository applications;

    public Object findAll() {
        return this.applications.findAll();
    }

    public Object getById(Long id) {
        Optional<Application> application=applications.findById(id);
        return application.isEmpty()? null:application.get();
    }

    public void addApplication(Application application) {
        this.applications.save(application);
    }

    public void updateApplication(Long id, Application application) {
        Application app= applications.findById(id).get();
        app.setAppName(application.getAppName());
        app.setDescription(application.getDescription());
        app.setCategory(application.getCategory());
        app.setDestinationDestination(application.getDestinationDestination());
        this.applications.save(app);
    }

    public void deleteApplication(Long id) {
        Application application = applications.findById(id).get();
        this.applications.delete(application);
    }
}
