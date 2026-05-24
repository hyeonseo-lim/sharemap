/* UserRepository 
- User 엔티티에 대한 데이터 액세스 레이어를 제공하는 인터페이스
- JpaRepository를 확장하여 기본 CRUD 메서드와 사용자 정의 쿼리 메서드를 사용할 수 있도록 함
- findByUsername: 사용자 이름으로 User 엔티티를 조회하는 메서드, Optional<User>를 반환하여 결과가 없을 경우에도 안전하게 처리할 수 있도록 함
- UserRepository는 AuthController에서 사용자 정보를 데이터베이스에 저장하고 조회하는 데 사용됨
- 향후 보안을 강화하기 위해 비밀번호 암호화 로직이 추가될 때, UserRepository는 암호화된 비밀번호를 저장하고 조회하는 역할을 계속 수행할 예정
*/

package com.map.sharemap;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}