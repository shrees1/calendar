package pojo;

import entity.User;
import enums.InviteeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Invitees {
    public User user;
    public InviteeStatus inviteeStatus = InviteeStatus.MAYBE;
}