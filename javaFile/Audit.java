public class Audit {
	private Integer auditid;
	private Integer applyid;
	private Integer audittype;
	private Integer auditstep;
	private String auditname;
	private Integer auditstatus;
	private String remark;
	private String auditor;
	private java.util.Date audittime;
	
	public Integer getAuditid() {
		return auditid;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}
	
	public Integer getApplyid() {
		return applyid;
	}

	public void setApplyid(Integer applyid) {
		this.applyid = applyid;
	}
	
	public Integer getAudittype() {
		return audittype;
	}

	public void setAudittype(Integer audittype) {
		this.audittype = audittype;
	}
	
	public Integer getAuditstep() {
		return auditstep;
	}

	public void setAuditstep(Integer auditstep) {
		this.auditstep = auditstep;
	}
	
	public String getAuditname() {
		return auditname;
	}

	public void setAuditname(String auditname) {
		this.auditname = auditname;
	}
	
	public Integer getAuditstatus() {
		return auditstatus;
	}

	public void setAuditstatus(Integer auditstatus) {
		this.auditstatus = auditstatus;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	public java.util.Date getAudittime() {
		return audittime;
	}

	public void setAudittime(java.util.Date audittime) {
		this.audittime = audittime;
	}
}