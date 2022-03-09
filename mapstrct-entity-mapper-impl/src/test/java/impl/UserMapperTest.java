package impl;


import com.abstractkamen.entities.User;
import com.abstractkamen.mappers.impl.UserMapper;
import com.abstractkamen.xsddomain.UserCommand;
import org.mapstruct.factory.Mappers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UserMapperTest {
    private UserMapper userMapper;

    @BeforeTest
    public void init() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void givenDto_fromDto_ShouldReturnExpectedUser() {
        final UserCommand dto = new UserCommand();
        dto.setLastName("Putin");
        dto.setLastName("E");
        dto.setEmail("lajno@gus.com");
        final User user = userMapper.fromDto(dto);

        Assert.assertEquals(user.getFirstName(), dto.getFirstName());
        Assert.assertEquals(user.getLastName(), dto.getLastName());
        Assert.assertEquals(user.getEmail(), dto.getEmail());

        Assert.assertNull(user.getId());
    }

    @Test
    public void givenUser_toDto_ShouldReturnExpectedUserCommand() {
        final User user = User.builder()
            .firstName("Kur")
            .lastName("Za")
            .email("russia@pomiina-jma.com")
            .build();
        final UserCommand dto = userMapper.toDto(user);

        Assert.assertEquals(dto.getFirstName(), user.getFirstName());
        Assert.assertEquals(dto.getLastName(), user.getLastName());
        Assert.assertEquals(dto.getEmail(), user.getEmail());
    }
}
