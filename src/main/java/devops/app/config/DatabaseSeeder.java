package devops.app.config;

import devops.app.entity.Role;
import devops.app.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            // Проверяем, есть ли записи в таблице ролей
            if (roleRepository.count() == 0) {
                // Добавляем роли, если таблица пуста
                Role roleUser = new Role();
                roleUser.setName("ROLE_USER");
                Role roleModerator = new Role();
                roleModerator.setName("ROLE_MODERATOR");
                roleRepository.save(roleUser);
                roleRepository.save(roleModerator);
                System.out.println("Роли были инициализированы.");
            } else {
                System.out.println("Роли уже существуют.");
            }
        };
    }
}
