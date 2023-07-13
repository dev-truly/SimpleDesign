package com.simpledesign.ndms.repository;

import com.simpledesign.ndms.entity.DefaultInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultInfoRepository extends JpaRepository<DefaultInfo, Integer> {
    @Query(value = "update tcm_cou_dngr_dsrisk set updde = current_timestamp() where 1=1", nativeQuery = true)
    @Modifying
    int updateTcmCouDngrDsrisk();

    @Query(value = "update sensor set update_dt = current_timestamp() WHERE length(ssr_id) = 4 and dscode is not null and SUBSTRING(dscode, 6, 1) IN ('0', '1', '2', '3', '4', '5', '6', '7', 'Z')", nativeQuery = true)
    @Modifying
    int updateTcmCouObsv();

    @Query(value = "update ssr_area_lvl set update_dt = current_timestamp() WHERE 1=1", nativeQuery = true)
    @Modifying
    int updateTcmCouThold();

    @Query(value = "update device d join device_cctv_dtl dcd on d.dvc_type_cd = 'cctv' and dcd.service_id is not null and d.dvc_id = dcd.dvc_id set d.update_dt = current_timestamp() where 1=1", nativeQuery = true)
    @Modifying
    int updateTcmCouCctvInfo();
}
