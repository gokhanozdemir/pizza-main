# Pizza Restoranı API Dokümantasyonu 🍕

Bu API, pizza restoranı yönetim sisteminin backend servisleridir. Ürün yönetimi, kategori işlemleri ve admin yetkilendirme süreçlerini içerir.

## 🛠 Gereksinimler
- Java 17
- Spring Boot 3.4.2
- PostgreSQL
- Next.js (Frontend için)

## 🔑 Authentication
Admin paneli için JWT tabanlı kimlik doğrulama kullanılmaktadır.

```javascript
// Auth isteği örneği
const login = async (username, password) => {
  try {
    const response = await axios.post('http://localhost:9000/pizza/api/auth/login', {
      username,
      password
    });
    const token = response.data.token;
    // Token'ı localStorage'a kaydet
    localStorage.setItem('token', token);
    return token;
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
};
```

## 📌 API Endpoints

### 🔓 Public Endpoints (Auth Gerektirmez)
```javascript
// Axios ile kullanım örnekleri

// Tüm kategorileri getir
const getCategories = async () => {
  const response = await axios.get('http://localhost:9000/pizza/api/category');
  return response.data;
};

// Basit kategori listesi (ürünler olmadan)
const getSimpleCategories = async () => {
  const response = await axios.get('http://localhost:9000/pizza/api/category/simple');
  return response.data;
};

// Tüm ürünleri getir
const getProducts = async () => {
  const response = await axios.get('http://localhost:9000/pizza/api/product');
  return response.data;
};

// Tek bir ürün getir
const getProduct = async (id) => {
  const response = await axios.get(`http://localhost:9000/pizza/api/product/${id}`);
  return response.data;
};
```

### 🔒 Protected Endpoints (Auth Gerektirir)
```javascript
// Axios ile kullanım örnekleri

// API için axios instance oluştur
const api = axios.create({
  baseURL: 'http://localhost:9000/pizza/api',
  headers: {
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  }
});

// Yeni kategori ekle
const addCategory = async (formData) => {
  const response = await api.post('/category', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  return response.data;
};

// Kategori güncelle
const updateCategory = async (id, formData) => {
  const response = await api.put(`/category/${id}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  return response.data;
};

// Kategori sil
const deleteCategory = async (id) => {
  await api.delete(`/category/${id}`);
};

// Yeni ürün ekle
const addProduct = async (formData) => {
  const response = await api.post('/product', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  return response.data;
};

// Ürün güncelle
const updateProduct = async (id, formData) => {
  const response = await api.put(`/product/${id}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  return response.data;
};

// Ürün sil
const deleteProduct = async (id) => {
  await api.delete(`/product/${id}`);
};
```

## 📦 Model Yapıları

### Category
```typescript
interface Category {
  id: number;
  name: string;
  img: string;
  products?: Product[];
}
```

### Product
```typescript
interface Product {
  id: number;
  name: string;
  rating: number;
  stock: number;
  price: number;
  img: string;
  categoryId: number;
}
```

## 🔧 Postman Kullanımı

1. Base URL: `http://localhost:9000/pizza/api`
2. Admin işlemleri için JWT token gereklidir:
    - Önce `/auth/login` endpoint'inden token alın
    - Token'ı Authorization header'ına ekleyin: `Bearer <token>`
3. Form-data ile dosya gönderirken:
    - Key: `image`
    - Type: `File`
    - Value: Dosya seçin

## ⚠️ Önemli Notlar

1. Public endpoint'ler (GET /product, GET /category) herkes tarafından erişilebilir
2. Admin işlemleri (POST, PUT, DELETE) için JWT authentication gereklidir
3. Resim yüklemeleri için multipart/form-data kullanılmalıdır
4. Token süresi 24 saattir

## 🌐 CORS Yapılandırması
API, varsayılan olarak `http://localhost:3000` (Next.js) origin'inden gelen isteklere izin verir.

## 💡 Hata Yönetimi
```javascript
try {
  // API isteği
} catch (error) {
  if (error.response) {
    // Server kaynaklı hata
    console.error('Error:', error.response.data.message);
  } else if (error.request) {
    // İstek hatası
    console.error('Request failed');
  } else {
    // Diğer hatalar
    console.error('Error:', error.message);
  }
}
```