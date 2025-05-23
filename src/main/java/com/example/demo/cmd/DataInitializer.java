package com.example.demo.cmd;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SettingRepository;
import com.example.demo.entity.Activity;
import com.example.demo.entity.MyUser;
import com.example.demo.entity.Role;
import com.example.demo.entity.Setting;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.repository.ActivityRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public void run(String... args) throws Exception {

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        // Create the admin user
        MyUser admin = new MyUser();
        admin.setAccount("admin");
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoles().add(adminRole);
        admin.getRoles().add(userRole);
        userRepository.save(admin);

        // create user01 user
        MyUser user01 = new MyUser();
        user01.setAccount("frank");
        user01.setName("frank");
        user01.setGender("男");
        user01.setEmail("frank@foo.com");
        user01.setPassword(passwordEncoder.encode("frank"));
        user01.getRoles().add(userRole);
        userRepository.save(user01);

        MyUser user02 = new MyUser();
        user02.setAccount("peter");
        user02.setName("peter");
        user02.setGender("男");
        user02.setEmail("peter@foo.com");
        user02.setPassword(passwordEncoder.encode("peter"));
        user02.getRoles().add(userRole);
        userRepository.save(user02);

        // Create the activity
        ArrayList<String> imageUrlList = new ArrayList<>(List.of(
            "https://img0.baidu.com/it/u=3520087324,3273355684&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500",
            "https://img1.baidu.com/it/u=1815061885,4152950649&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
            "https://img1.baidu.com/it/u=3802563539,3850812963&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
            "https://img2.baidu.com/it/u=2129477846,3076805512&fm=253&fmt=auto&app=120&f=JPEG?w=727&h=500",
            "https://img2.baidu.com/it/u=1827897933,1633226238&fm=253&fmt=auto&app=120&f=JPEG?w=887&h=500",
            "https://img2.baidu.com/it/u=2827799099,748701945&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500",
            "https://img0.baidu.com/it/u=2070603321,2370563969&fm=253&fmt=auto&app=120&f=JPEG?w=666&h=500",
            "https://img0.baidu.com/it/u=4187758802,2712882818&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
            "https://img0.baidu.com/it/u=877753991,4110909585&fm=253&fmt=auto&app=120&f=JPEG?w=666&h=500",
            "https://img1.baidu.com/it/u=1845921581,2220313242&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
            "https://img1.baidu.com/it/u=2523834233,1366854529&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
            "https://img1.baidu.com/it/u=2796489202,4130557273&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
            "https://img0.baidu.com/it/u=2992772449,1959863856&fm=253&fmt=auto?w=1185&h=800",
            "https://img0.baidu.com/it/u=2992772449,1959863856&fm=253&fmt=auto?w=1185&h=800",
            "https://img1.baidu.com/it/u=1295106270,2596846191&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500",
            "https://img1.baidu.com/it/u=2902771306,1939150963&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500",
            "https://img0.baidu.com/it/u=812737043,498725803&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500",
            "https://img0.baidu.com/it/u=2959920308,1249361253&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
            "https://img1.baidu.com/it/u=3982782803,591550653&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500"
        ));
    
        int i = 1;
        for (String imageUrl : imageUrlList) {
            Activity activity = new Activity();
            activity.setStatus("已完成");
            activity.setTitle("Activity A" + i);
            if (i % 2 == 0) {
                activity.setCreator(user01);
                activity.addParticipant(user02);
            } else {
                activity.setCreator(user02);
                activity.addParticipant(user01);
            }
            activity.setImageURL(imageUrl);
            activity.setVolunteerHour(2);
            activity.setStartDate(LocalDate.now());
            activity.setEndDate(LocalDate.now().plusDays(7));
            activity.setVolunteerCriteria("限男性");
            activityRepository.save(activity);
            i += 1;
        }



        ArrayList<String> imageUrlListActive = new ArrayList<>(List.of(
            "https://img2.baidu.com/it/u=953469906,2247852463&fm=253&fmt=auto&app=120&f=JPEG?w=569&h=425",
            "https://img1.baidu.com/it/u=2692614438,269224415&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667",
            "https://img0.baidu.com/it/u=2946175902,2900077633&fm=253&fmt=auto&app=138&f=JPEG?w=1112&h=500",
            "https://img2.baidu.com/it/u=1827897933,1633226238&fm=253&fmt=auto&app=120&f=JPEG?w=887&h=500",
            "https://img0.baidu.com/it/u=3927235320,2738771805&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
            "https://img2.baidu.com/it/u=2004150941,954971513&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
            "https://img0.baidu.com/it/u=634458776,259538535&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667"
        ));
            
        
        i = 1;
        for (String imageUrl : imageUrlListActive) {
            Activity activity = new Activity();
            activity.setStatus("已通过");
            activity.setTitle("Activity B" + i);
            if (i % 2 == 0) {
                activity.setCreator(user01);
                activity.addParticipant(user02);
            } else {
                activity.setCreator(user02);
                activity.addParticipant(user01);
            }
            activity.setImageURL(imageUrl);
            activity.setVolunteerHour(3);
            activity.setStartDate(LocalDate.now());
            activity.setEndDate(LocalDate.now().plusDays(7));
            activity.setVolunteerCriteria("限男性");
            activityRepository.save(activity);
            i += 1;
        }
        
        
        ArrayList<String> imageUrlListCreated = new ArrayList<>(List.of(
            "https://img1.baidu.com/it/u=3750429607,2341929747&fm=253&app=138&f=JPEG?w=500&h=667",
            "https://img1.baidu.com/it/u=583590770,2353897149&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500",
            "https://img1.baidu.com/it/u=2523834233,1366854529&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
            "https://img0.baidu.com/it/u=69091635,1171280379&fm=253&fmt=auto&app=138&f=JPEG?w=1067&h=800",
            "https://img0.baidu.com/it/u=2919104076,2587240217&fm=253&app=138&f=JPEG?w=1067&h=800",
            "https://img2.baidu.com/it/u=1246067024,2736488947&fm=253&app=138&f=JPEG?w=1067&h=800",
            "https://img2.baidu.com/it/u=1896746629,1719761148&fm=253&app=138&f=JPEG?w=500&h=667",
            "https://img0.baidu.com/it/u=3306841619,680634643&fm=253&app=138&f=JPEG?w=500&h=667"
        ));
        
        i = 1;
        for (String imageUrl : imageUrlListCreated) {
            Activity activity = new Activity();
            activity.setTitle("Activity C" + i);
            if (i % 2 == 0) {
                activity.setCreator(user01);
            } else {
                activity.setCreator(user02);
            }
            activity.setImageURL(imageUrl);
            activity.setVolunteerHour(3);
            activity.setStartDate(LocalDate.now());
            activity.setEndDate(LocalDate.now().plusDays(7));
            activity.setVolunteerCriteria("限男性");
            activityRepository.save(activity);
            i += 1;
        }


        Setting setting = new Setting();
        setting.setHostHourRequirement(20);
        settingRepository.save(setting);
    }
}