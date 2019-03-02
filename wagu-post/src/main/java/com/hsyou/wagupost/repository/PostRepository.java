package com.hsyou.wagupost.repository;

import com.hsyou.wagupost.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/*구조체에 이미 Repository 어노테이션이 달려있어서 여기에 어노테이션을 달지 않아도 되지만
* 달게되면 Exception을 DataAccessException으로 변환해주기 때문에 예외만 보고 원인을 파악하기 쉬워진다.
*/
public interface PostRepository extends JpaRepository<Post,Long> {

    //Pageable
}
