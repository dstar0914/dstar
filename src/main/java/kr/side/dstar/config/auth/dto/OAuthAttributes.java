package kr.side.dstar.config.auth.dto;

import lombok.Getter;

@Getter
public class OAuthAttributes {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private String phone;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String phone) {
//        this.attributes         = attributes;
//        this.nameAttributeKey   = nameAttributeKey;
//        this.name               = name;
//        this.email              = email;
//        this.phone              = phone;
//    }
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public Member toEntity() {
//        return Member.builder()
//                .name(name)
//                .email(email)
//                .status()
//                .status(Set<MemberStatus.AUTHORIZED>)
//                .build();
//    }
}
