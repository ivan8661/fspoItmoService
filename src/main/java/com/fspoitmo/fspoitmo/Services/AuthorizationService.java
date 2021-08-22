package com.fspoitmo.fspoitmo.Services;


import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import com.fspoitmo.fspoitmo.Entities.Repositories.PupilGroupRepository;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private ParseService parseService;

    private PupilGroupRepository pupilGroupRepository;



    @Autowired
    public AuthorizationService(ParseService parseService, PupilGroupRepository pupilGroupRepository) {
        this.parseService = parseService;
        this.pupilGroupRepository = pupilGroupRepository;
    }

    public String regFSPOITMOUser(String regData) throws JSONException, UserException {

        JSONObject reg = new JSONObject(regData);
        String login = reg.optString("serviceLogin");
        String password = reg.optString("servicePassword");
        parseService.setMainPage(login, password);
        String numberGroup = parseService.getGroup();

        String snp = parseService.getSNP();
        String lastName = snp.substring(0, snp.indexOf(" "));
        String firstName = snp.substring(lastName.length()+1);
        firstName = firstName.substring(0, firstName.indexOf(" "));
        String id = parseService.getId();
        String avatarURL = parseService.getAvatar();


        JSONObject user = new JSONObject();

        user.put("_id", DigestUtils.sha256Hex(login));
        user.put("serviceLogin", login);
        user.put("servicePassword", password);
        user.put("lastname", lastName);
        user.put("firstname", firstName);
        user.put("universityId", "FSPOITMO");
        if(pupilGroupRepository.findPupilGroupByName(numberGroup)!=null) {
            PupilGroup pupilGroup = pupilGroupRepository.findPupilGroupByName(numberGroup);
            user.put("groupId", pupilGroup.getId());
            user.put("groupName", pupilGroup.getName());
        }

        return user.toString();
    }
}
