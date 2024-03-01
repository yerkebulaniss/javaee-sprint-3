package kz.bitlab.academy.news.entity;

import kz.bitlab.academy.categories.entity.CategoryEntity;

import java.time.LocalDateTime;

public class NewsEntity {

    private Long id;
    private LocalDateTime postDate = LocalDateTime.now();
    private CategoryEntity category;
    private String title;
    private String content;

    public NewsEntity(){}

    public NewsEntity(CategoryEntity category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
