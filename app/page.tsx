"use client";
import { useState, useEffect } from "react";

export default function Portfolio() {
  const [activeSection, setActiveSection] = useState("home");
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  const projects = [
    {
      title: "Backtracking Sudoku Solver",
      tech: ["Python", "Tkinter"],
      desc: "A Sudoku game with an automated solver using recursive backtracking algorithm and interactive GUI.",
      icon: "⬛",
    },
    {
      title: "Puissance4 MAS",
      tech: ["Java", "JADE", "Swing"],
      desc: "Connect Four strategy game featuring an intelligent Multi-Agent System with autonomous decision-making agents.",
      icon: "🔴",
    },
    {
      title: "Expert System",
      tech: ["Python", "Tkinter"],
      desc: "Forward and backward chaining inference engine with a clean UI for expert diagnostics and advice.",
      icon: "🧠",
    },
    {
      title: "Pascal Compiler Frontend",
      tech: ["C", "Flex", "Bison"],
      desc: "Compiler frontend for a Pascal-like language with lexer, parser, and syntax validation.",
      icon: "⚙️",
    },
    {
      title: "Sport Meetings Portal",
      tech: ["PHP", "MySQL"],
      desc: "Web platform connecting sports enthusiasts by location and skill level with event scheduling.",
      icon: "🏃",
    },
    {
      title: "Apartment Rental System",
      tech: ["Python", "Kivy", "MySQL"],
      desc: "Desktop app for rental management with dual GUI, tenant data, and payment tracking.",
      icon: "🏠",
    },
  ];

  const skills = [
    { category: "Languages", items: ["Python", "Java", "C", "PHP", "JavaScript"] },
    { category: "Web", items: ["HTML/CSS", "PHP", "MySQL", "Next.js (learning)"] },
    { category: "Mobile", items: ["Kivy", "Android dev"] },
    { category: "Tools", items: ["Git", "VS Code", "Packet Tracer", "eNSP"] },
    { category: "CS Concepts", items: ["Algorithms", "OOP", "AI", "Networking", "Security"] },
  ];

  return (
    <>
      <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Syne:wght@400;600;700;800&family=DM+Mono:ital,wght@0,300;0,400;1,300&display=swap');

        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
          --bg: #0a0a0a;
          --surface: #111111;
          --border: #1e1e1e;
          --accent: #e8ff47;
          --accent2: #ff6b35;
          --text: #f0f0f0;
          --muted: #666;
          --card: #141414;
        }

        html { scroll-behavior: smooth; }

        body {
          background: var(--bg);
          color: var(--text);
          font-family: 'DM Mono', monospace;
          overflow-x: hidden;
        }

        .noise {
          position: fixed;
          inset: 0;
          background-image: url("data:image/svg+xml,%3Csvg viewBox='0 0 256 256' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='noise'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23noise)' opacity='0.04'/%3E%3C/svg%3E");
          pointer-events: none;
          z-index: 9999;
          opacity: 0.4;
        }

        nav {
          position: fixed;
          top: 0;
          left: 0;
          right: 0;
          z-index: 100;
          padding: 1.2rem 3rem;
          display: flex;
          justify-content: space-between;
          align-items: center;
          border-bottom: 1px solid var(--border);
          background: rgba(10,10,10,0.85);
          backdrop-filter: blur(12px);
        }

        .nav-logo {
          font-family: 'Syne', sans-serif;
          font-weight: 800;
          font-size: 1.1rem;
          letter-spacing: -0.02em;
          color: var(--accent);
        }

        .nav-links {
          display: flex;
          gap: 2.5rem;
          list-style: none;
        }

        .nav-links a {
          color: var(--muted);
          text-decoration: none;
          font-size: 0.75rem;
          letter-spacing: 0.1em;
          text-transform: uppercase;
          transition: color 0.2s;
        }

        .nav-links a:hover { color: var(--accent); }

        /* HERO */
        .hero {
          min-height: 100vh;
          display: grid;
          grid-template-columns: 1fr 1fr;
          align-items: center;
          padding: 8rem 3rem 4rem;
          gap: 4rem;
          max-width: 1200px;
          margin: 0 auto;
        }

        .hero-label {
          font-size: 0.7rem;
          letter-spacing: 0.2em;
          text-transform: uppercase;
          color: var(--accent);
          margin-bottom: 1.5rem;
          opacity: ${mounted ? 1 : 0};
          transform: translateY(${mounted ? 0 : 20}px);
          transition: all 0.6s ease 0.1s;
        }

        .hero-name {
          font-family: 'Syne', sans-serif;
          font-weight: 800;
          font-size: clamp(3rem, 6vw, 5.5rem);
          line-height: 0.95;
          letter-spacing: -0.03em;
          margin-bottom: 1.5rem;
          opacity: ${mounted ? 1 : 0};
          transform: translateY(${mounted ? 0 : 30}px);
          transition: all 0.7s ease 0.2s;
        }

        .hero-name span {
          color: var(--accent);
          display: block;
        }

        .hero-desc {
          color: var(--muted);
          font-size: 0.85rem;
          line-height: 1.8;
          max-width: 420px;
          margin-bottom: 2.5rem;
          opacity: ${mounted ? 1 : 0};
          transition: all 0.7s ease 0.35s;
          transform: translateY(${mounted ? 0 : 20}px);
        }

        .hero-cta {
          display: flex;
          gap: 1rem;
          flex-wrap: wrap;
          opacity: ${mounted ? 1 : 0};
          transition: all 0.7s ease 0.45s;
          transform: translateY(${mounted ? 0 : 20}px);
        }

        .btn-primary {
          background: var(--accent);
          color: #000;
          padding: 0.75rem 1.8rem;
          font-family: 'Syne', sans-serif;
          font-weight: 700;
          font-size: 0.8rem;
          letter-spacing: 0.05em;
          text-transform: uppercase;
          border: none;
          cursor: pointer;
          text-decoration: none;
          display: inline-block;
          transition: transform 0.2s, background 0.2s;
        }

        .btn-primary:hover { transform: translateY(-2px); background: #f0ff60; }

        .btn-ghost {
          background: transparent;
          color: var(--text);
          padding: 0.75rem 1.8rem;
          font-family: 'Syne', sans-serif;
          font-weight: 700;
          font-size: 0.8rem;
          letter-spacing: 0.05em;
          text-transform: uppercase;
          border: 1px solid var(--border);
          cursor: pointer;
          text-decoration: none;
          display: inline-block;
          transition: border-color 0.2s, color 0.2s;
        }

        .btn-ghost:hover { border-color: var(--accent); color: var(--accent); }

        .hero-right {
          display: flex;
          flex-direction: column;
          gap: 1.5rem;
          opacity: ${mounted ? 1 : 0};
          transition: all 0.8s ease 0.5s;
          transform: translateX(${mounted ? 0 : 30}px);
        }

        .stat-card {
          background: var(--card);
          border: 1px solid var(--border);
          padding: 1.5rem;
          display: flex;
          align-items: center;
          gap: 1.5rem;
        }

        .stat-number {
          font-family: 'Syne', sans-serif;
          font-size: 2.5rem;
          font-weight: 800;
          color: var(--accent);
          line-height: 1;
        }

        .stat-label {
          font-size: 0.7rem;
          color: var(--muted);
          letter-spacing: 0.1em;
          text-transform: uppercase;
        }

        .available-badge {
          display: inline-flex;
          align-items: center;
          gap: 0.5rem;
          background: rgba(232,255,71,0.08);
          border: 1px solid rgba(232,255,71,0.2);
          padding: 0.4rem 1rem;
          font-size: 0.7rem;
          color: var(--accent);
          letter-spacing: 0.1em;
          text-transform: uppercase;
          width: fit-content;
        }

        .dot {
          width: 6px;
          height: 6px;
          background: var(--accent);
          border-radius: 50%;
          animation: pulse 2s infinite;
        }

        @keyframes pulse {
          0%, 100% { opacity: 1; }
          50% { opacity: 0.3; }
        }

        /* SECTIONS */
        section {
          max-width: 1200px;
          margin: 0 auto;
          padding: 6rem 3rem;
        }

        .section-header {
          display: flex;
          align-items: baseline;
          gap: 1.5rem;
          margin-bottom: 4rem;
        }

        .section-num {
          font-size: 0.7rem;
          color: var(--accent);
          letter-spacing: 0.15em;
        }

        .section-title {
          font-family: 'Syne', sans-serif;
          font-size: clamp(1.8rem, 3vw, 2.8rem);
          font-weight: 800;
          letter-spacing: -0.02em;
        }

        .section-line {
          flex: 1;
          height: 1px;
          background: var(--border);
        }

        /* PROJECTS */
        .projects-grid {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(340px, 1fr));
          gap: 1px;
          background: var(--border);
        }

        .project-card {
          background: var(--card);
          padding: 2rem;
          transition: background 0.3s;
          cursor: default;
        }

        .project-card:hover { background: #1a1a1a; }

        .project-icon {
          font-size: 1.8rem;
          margin-bottom: 1.2rem;
          display: block;
        }

        .project-title {
          font-family: 'Syne', sans-serif;
          font-weight: 700;
          font-size: 1rem;
          margin-bottom: 0.75rem;
          letter-spacing: -0.01em;
        }

        .project-desc {
          font-size: 0.78rem;
          color: var(--muted);
          line-height: 1.7;
          margin-bottom: 1.5rem;
        }

        .tech-tags {
          display: flex;
          flex-wrap: wrap;
          gap: 0.4rem;
        }

        .tech-tag {
          background: transparent;
          border: 1px solid var(--border);
          color: var(--muted);
          padding: 0.2rem 0.6rem;
          font-size: 0.65rem;
          letter-spacing: 0.08em;
          text-transform: uppercase;
          transition: border-color 0.2s, color 0.2s;
        }

        .project-card:hover .tech-tag {
          border-color: rgba(232,255,71,0.3);
          color: var(--accent);
        }

        /* SKILLS */
        .skills-grid {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
          gap: 2rem;
        }

        .skill-group-title {
          font-size: 0.65rem;
          letter-spacing: 0.15em;
          text-transform: uppercase;
          color: var(--accent);
          margin-bottom: 1rem;
          padding-bottom: 0.5rem;
          border-bottom: 1px solid var(--border);
        }

        .skill-item {
          font-size: 0.8rem;
          color: var(--muted);
          padding: 0.3rem 0;
          transition: color 0.2s;
        }

        .skill-item:hover { color: var(--text); }

        /* ABOUT */
        .about-grid {
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 4rem;
          align-items: start;
        }

        .about-text p {
          font-size: 0.85rem;
          color: var(--muted);
          line-height: 1.9;
          margin-bottom: 1.2rem;
        }

        .about-text strong { color: var(--text); }

        .info-list {
          display: flex;
          flex-direction: column;
          gap: 1rem;
        }

        .info-row {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 1rem 0;
          border-bottom: 1px solid var(--border);
          font-size: 0.78rem;
        }

        .info-key {
          color: var(--muted);
          letter-spacing: 0.08em;
          text-transform: uppercase;
          font-size: 0.68rem;
        }

        .info-val { color: var(--text); }

        /* CONTACT */
        .contact-inner {
          background: var(--card);
          border: 1px solid var(--border);
          padding: 4rem;
          text-align: center;
        }

        .contact-title {
          font-family: 'Syne', sans-serif;
          font-size: clamp(2rem, 4vw, 3.5rem);
          font-weight: 800;
          letter-spacing: -0.03em;
          margin-bottom: 1rem;
        }

        .contact-title span { color: var(--accent); }

        .contact-sub {
          color: var(--muted);
          font-size: 0.82rem;
          margin-bottom: 2.5rem;
        }

        .contact-links {
          display: flex;
          gap: 1rem;
          justify-content: center;
          flex-wrap: wrap;
        }

        footer {
          border-top: 1px solid var(--border);
          padding: 2rem 3rem;
          display: flex;
          justify-content: space-between;
          align-items: center;
          max-width: 1200px;
          margin: 0 auto;
          font-size: 0.7rem;
          color: var(--muted);
        }

        @media (max-width: 768px) {
          nav { padding: 1rem 1.5rem; }
          .nav-links { display: none; }
          .hero { grid-template-columns: 1fr; padding: 6rem 1.5rem 3rem; }
          .hero-right { display: none; }
          section { padding: 4rem 1.5rem; }
          .about-grid { grid-template-columns: 1fr; }
          .contact-inner { padding: 2.5rem 1.5rem; }
        }
      `}</style>

      <div className="noise" />

      <nav>
        <div className="nav-logo">AB</div>
        <ul className="nav-links">
          <li><a href="#projects">Projects</a></li>
          <li><a href="#skills">Skills</a></li>
          <li><a href="#about">About</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
      </nav>

      {/* HERO */}
      <div className="hero">
        <div>
          <div className="hero-label">— Full Stack Developer</div>
          <h1 className="hero-name">
            Abdallah<span>Bazia.</span>
          </h1>
          <p className="hero-desc">
            Computer Science graduate from Jijel, Algeria. I build intelligent software —
            from AI expert systems and compilers to web platforms and mobile apps.
            Currently leveling up in React & Next.js.
          </p>
          <div className="hero-cta">
            <a href="#projects" className="btn-primary">View Projects</a>
            <a href="https://github.com/abdallah-bazia/portfolio" target="_blank" rel="noreferrer" className="btn-ghost">GitHub ↗</a>
          </div>
        </div>

        <div className="hero-right">
          <div className="available-badge">
            <span className="dot" />
            Open to opportunities
          </div>
          <div className="stat-card">
            <div className="stat-number">6+</div>
            <div>
              <div className="stat-label">Projects Built</div>
              <div style={{fontSize:'0.72rem', color:'#444', marginTop:'0.2rem'}}>Across web, desktop & AI</div>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-number">6.5</div>
            <div>
              <div className="stat-label">IELTS Score</div>
              <div style={{fontSize:'0.72rem', color:'#444', marginTop:'0.2rem'}}>C1 Listening & Reading</div>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-number">3</div>
            <div>
              <div className="stat-label">Languages</div>
              <div style={{fontSize:'0.72rem', color:'#444', marginTop:'0.2rem'}}>Arabic · French · English</div>
            </div>
          </div>
        </div>
      </div>

      {/* PROJECTS */}
      <section id="projects">
        <div className="section-header">
          <span className="section-num">01</span>
          <h2 className="section-title">Projects</h2>
          <div className="section-line" />
        </div>
        <div className="projects-grid">
          {projects.map((p, i) => (
            <div className="project-card" key={i}>
              <span className="project-icon">{p.icon}</span>
              <div className="project-title">{p.title}</div>
              <p className="project-desc">{p.desc}</p>
              <div className="tech-tags">
                {p.tech.map((t) => <span className="tech-tag" key={t}>{t}</span>)}
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* SKILLS */}
      <section id="skills" style={{borderTop: '1px solid var(--border)'}}>
        <div className="section-header">
          <span className="section-num">02</span>
          <h2 className="section-title">Skills</h2>
          <div className="section-line" />
        </div>
        <div className="skills-grid">
          {skills.map((s, i) => (
            <div key={i}>
              <div className="skill-group-title">{s.category}</div>
              {s.items.map((item) => (
                <div className="skill-item" key={item}>— {item}</div>
              ))}
            </div>
          ))}
        </div>
      </section>

      {/* ABOUT */}
      <section id="about" style={{borderTop: '1px solid var(--border)'}}>
        <div className="section-header">
          <span className="section-num">03</span>
          <h2 className="section-title">About</h2>
          <div className="section-line" />
        </div>
        <div className="about-grid">
          <div className="about-text">
            <p>
             I'm a <strong>Computer Science graduate</strong> from Mohamed Seddik Benyahia University
             of Jijel, Algeria. Currently pursuing a <strong>Master's in Network & Security (M1)</strong>,
             building on my bachelor's degree completed with distinction.
            </p>
            <p>
              My background spans <strong>software engineering, artificial intelligence, database systems,
              and mobile development</strong>. I enjoy working on projects that require both algorithmic
              thinking and clean user-facing design.
            </p>
            <p>
              I'm currently expanding into <strong>React and Next.js</strong> to bring my full-stack
              capabilities to modern web development. Fast learner, team player, trilingual.
            </p>
          </div>
          <div className="info-list">
            {[
              ["Location", "Jijel, Algeria"],
              ["Degree", "BSc Computer Science → M1 Network & Security"],
              ["University", "MSB University of Jijel"],
              ["Graduation", "June 2025"],
              ["English", "IELTS 6.5 (B2/C1)"],
              ["Email", "abdellahbazia888@gmail.com"],
            ].map(([k, v]) => (
              <div className="info-row" key={k}>
                <span className="info-key">{k}</span>
                <span className="info-val">{v}</span>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CONTACT */}
      <section id="contact" style={{borderTop: '1px solid var(--border)'}}>
        <div className="section-header">
          <span className="section-num">04</span>
          <h2 className="section-title">Contact</h2>
          <div className="section-line" />
        </div>
        <div className="contact-inner">
          <h3 className="contact-title">Let's work<span> together.</span></h3>
          <p className="contact-sub">Open to frontend roles, collaborations, and new challenges.</p>
          <div className="contact-links">
            <a href="mailto:abdellahbazia888@gmail.com" className="btn-primary">Send Email</a>
            <a href="https://github.com/abdallah-bazia/portfolio" target="_blank" rel="noreferrer" className="btn-ghost">GitHub ↗</a>
          </div>
        </div>
      </section>

      <footer>
        <span>© 2025 Abdallah Bazia</span>
        <span>Built with Next.js</span>
      </footer>
    </>
  );
}