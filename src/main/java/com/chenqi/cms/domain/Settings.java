package com.chenqi.cms.domain;

import java.io.Serializable;

public class Settings implements Serializable {
    private Integer id;

    private String siteDomain;

    private String siteName;

    private Integer articleListSize;

    private Integer slideSize;

    private String adminUsername;

    private String adminPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain == null ? null : siteDomain.trim();
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }

    public Integer getArticleListSize() {
        return articleListSize;
    }

    public void setArticleListSize(Integer articleListSize) {
        this.articleListSize = articleListSize;
    }

    public Integer getSlideSize() {
        return slideSize;
    }

    public void setSlideSize(Integer slideSize) {
        this.slideSize = slideSize;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername == null ? null : adminUsername.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminPassword == null) ? 0 : adminPassword.hashCode());
		result = prime * result + ((adminUsername == null) ? 0 : adminUsername.hashCode());
		result = prime * result + ((articleListSize == null) ? 0 : articleListSize.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((siteDomain == null) ? 0 : siteDomain.hashCode());
		result = prime * result + ((siteName == null) ? 0 : siteName.hashCode());
		result = prime * result + ((slideSize == null) ? 0 : slideSize.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Settings other = (Settings) obj;
		if (adminPassword == null) {
			if (other.adminPassword != null)
				return false;
		} else if (!adminPassword.equals(other.adminPassword))
			return false;
		if (adminUsername == null) {
			if (other.adminUsername != null)
				return false;
		} else if (!adminUsername.equals(other.adminUsername))
			return false;
		if (articleListSize == null) {
			if (other.articleListSize != null)
				return false;
		} else if (!articleListSize.equals(other.articleListSize))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (siteDomain == null) {
			if (other.siteDomain != null)
				return false;
		} else if (!siteDomain.equals(other.siteDomain))
			return false;
		if (siteName == null) {
			if (other.siteName != null)
				return false;
		} else if (!siteName.equals(other.siteName))
			return false;
		if (slideSize == null) {
			if (other.slideSize != null)
				return false;
		} else if (!slideSize.equals(other.slideSize))
			return false;
		return true;
	}
    
}